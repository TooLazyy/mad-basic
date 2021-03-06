package ru.wearemad.mad_base.files

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.withContext
import ru.wearemad.mad_base.coroutines.DispatchersProvider
import ru.wearemad.mad_base.coroutines.RequestResult
import ru.wearemad.mad_base.coroutines.wrapResult
import java.io.File
import java.io.FileOutputStream

class PictureProvider(
    private val context: Context,
    private val dispatchersProvider: DispatchersProvider,
    private val fileHelper: FileHelper
) {

    fun createTempImageFile(): File = fileHelper.createTempImageFile()

    suspend fun saveBitmapToFile(image: Bitmap): Unit =
        withContext(dispatchersProvider.io()) {
            wrapResult {
                val file = createTempImageFile()
                FileOutputStream(file).use { fos ->
                    image.compress(Bitmap.CompressFormat.JPEG, 80, fos)
                }
                file
            }
        }

    suspend fun saveFileToAppDir(
        contentUri: Uri,
        filePathCreator: Context.() -> String = { createTempImageFile().absolutePath }
    ): RequestResult<String> = withContext(dispatchersProvider.default()) {
        wrapResult {
            val resolver = context.contentResolver
            val input = resolver.openInputStream(contentUri) ?: throw Exception("smth went wrong")
            val tempFilePath = filePathCreator(context)
            val file = File(tempFilePath)
            if (file.exists()) {
                file.delete()
            }
            input.use { `is` ->
                FileOutputStream(file).use { fos ->
                    `is`.copyTo(fos)
                }
            }
            if (file.exists()) {
                file.absolutePath
            } else {
                throw Exception("smth went wrong")
            }
        }
    }
}