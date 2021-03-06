package ru.wearemad.mad_base.message

import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

interface MessageController {

    companion object {

        private const val DEFAULT_SNACK_DURATION: Int = Snackbar.LENGTH_LONG
    }

    fun showToast(
        text: String,
        duration: Int = Toast.LENGTH_LONG
    )

    fun showToast(
        @StringRes textResId: Int,
        duration: Int = Toast.LENGTH_LONG
    )

    fun showSnack(
        message: String,
        @ColorRes backgroundColor: Int? = null,
        @StringRes actionStringId: Int? = null,
        @ColorRes buttonColor: Int? = null,
        duration: Int = DEFAULT_SNACK_DURATION,
        listener: (view: View) -> Unit = {}
    )

    fun showSnack(
        @StringRes stringId: Int,
        @ColorRes backgroundColor: Int? = null,
        @StringRes actionStringId: Int? = null,
        @ColorRes buttonColor: Int? = null,
        duration: Int = DEFAULT_SNACK_DURATION,
        listener: (view: View) -> Unit = {}
    )

    fun cancelAll()
}