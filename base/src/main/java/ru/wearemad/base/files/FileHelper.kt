package ru.wearemad.base.files

import android.content.Context
import ru.wearemad.base.files.FileUtils
import java.io.File

class FileHelper(
    private val context: Context
) {

    fun createTempImageFile(): File =
        FileUtils.createTempImageFile(context)
}