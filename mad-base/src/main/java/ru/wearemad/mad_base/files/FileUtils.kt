package ru.wearemad.mad_base.files

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    private val fileStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

    private const val IMAGE_EXT = ".jpg"
    private const val IMAGE_PREFIX = "image_"

    fun createTempImageFile(context: Context): File {
        val fileTimestamp = fileStamp.format(Date())
        val imageFileName = IMAGE_PREFIX + fileTimestamp + "_"
        val directory = context.externalCacheDir
        return File.createTempFile(
            imageFileName,
            IMAGE_EXT,
            directory
        )
    }
}