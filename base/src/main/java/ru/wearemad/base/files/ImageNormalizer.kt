package ru.wearemad.base.files

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.withContext
import ru.wearemad.base.coroutines.DispatchersProvider
import ru.wearemad.base.coroutines.RequestResult
import ru.wearemad.base.coroutines.wrapResult
import java.io.File
import java.io.FileOutputStream

private const val DEFAULT_IMAGE_COMPRESSION = 80

class ImageNormalizer(
    private val fileHelper: FileHelper,
    private val dispatchersProvider: DispatchersProvider
) {

    suspend fun normalizeAndCompress(
        image: String,
        imageCompression: Int = DEFAULT_IMAGE_COMPRESSION,
        imageFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
    ): RequestResult<File> = withContext(dispatchersProvider.io()) {
        wrapResult {
            val exif = createExif(image)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            normalizeImage(
                File(image),
                orientation,
                imageCompression,
                imageFormat
            )
        }
    }

    private fun createExif(image: String): ExifInterface {
        return try {
            ExifInterface(image)
        } catch (ex: Exception) {
            throw IllegalStateException("Cannot get Exif")
        }
    }

    private fun normalizeImage(
        originalImage: File,
        orientation: Int,
        imageCompression: Int,
        imageFormat: Bitmap.CompressFormat
    ): File {
        val savedFile = saveImageWithOrientation(
            originalImage,
            orientation,
            imageCompression,
            imageFormat
        )
        return savedFile ?: originalImage
    }

    private fun saveImageWithOrientation(
        originalImage: File,
        orientation: Int,
        imageCompression: Int,
        imageFormat: Bitmap.CompressFormat
    ): File? {
        return try {
            val newBitmap = createNormalizedBitmap(originalImage, orientation)
            val newFile = fileHelper.createTempImageFile()
            FileOutputStream(newFile).use {
                newBitmap.compress(
                    imageFormat,
                    imageCompression,
                    it
                )
            }
            if (!newBitmap.isRecycled) {
                newBitmap.recycle()
            }
            newFile
        } catch (ex: Exception) {
            null
        }
    }

    private fun createNormalizedBitmap(
        originalImage: File,
        orientation: Int
    ): Bitmap {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
            inMutable = true
            inPreferredConfig = Bitmap.Config.RGB_565
        }
        val original = BitmapFactory.decodeFile(originalImage.absolutePath, options)
        val matrix = Matrix().apply { setExifOrientation(orientation) }
        return Bitmap.createBitmap(
            original,
            0,
            0,
            original.width,
            original.height,
            matrix,
            true
        ).apply {
            if (this != original) {
                if (!original.isRecycled) {
                    original.recycle()
                }
            }
        }
    }
}

private fun Matrix.setExifOrientation(orientation: Int) {
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270F)
        ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180F)
        ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90F)
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> preScale(-1.0F, 1.0F)
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> preScale(1.0F, -1.0F)
        ExifInterface.ORIENTATION_TRANSPOSE -> {
            preRotate(-90F)
            preScale(-1.0F, 1.0F)
        }
        ExifInterface.ORIENTATION_TRANSVERSE -> {
            preRotate(90F)
            preScale(-1.0F, 1.0F)
        }
    }
}