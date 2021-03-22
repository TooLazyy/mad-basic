package ru.wearemad.mad_base.message

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.wearemad.mad_base.activity.CurrentActivityHolder
import ru.wearemad.mad_base.message.factory.SnackbarFactory
import ru.wearemad.mad_base.message.factory.ToastFactory

class DefaultMessageController(
    private val activityHolder: CurrentActivityHolder,
    private val snackbarFactory: SnackbarFactory,
    private val toastFactory: ToastFactory
) : MessageController {

    private var toast: Toast? = null
    private var snackbar: Snackbar? = null

    override fun showToast(text: String, duration: Int) {
        cancelToast()
        toast = toastFactory.createToast(text, duration)
        toast?.show()
    }

    override fun showToast(textResId: Int, duration: Int) {
        showToast(
            activityHolder.currentActivity?.resources?.getString(textResId) ?: "",
            duration
        )
    }

    override fun showSnack(
        message: String,
        backgroundColor: Int?,
        actionStringId: Int?,
        buttonColor: Int?,
        duration: Int,
        listener: (view: View) -> Unit
    ) {
        cancelSnack()
        snackbar = snackbarFactory.createSnackbar(
            message,
            backgroundColor,
            actionStringId,
            buttonColor,
            duration,
            listener
        )
        snackbar?.show()
    }

    override fun showSnack(
        stringId: Int,
        backgroundColor: Int?,
        actionStringId: Int?,
        buttonColor: Int?,
        duration: Int,
        listener: (view: View) -> Unit
    ) {
        showSnack(
            activityHolder.currentActivity?.resources?.getString(stringId) ?: "",
            backgroundColor,
            actionStringId,
            buttonColor,
            duration,
            listener
        )
    }

    override fun cancelAll() {
        cancelToast()
        cancelSnack()
    }

    private fun cancelToast() {
        toast?.cancel()
        toast = null
    }

    private fun cancelSnack() {
        snackbar?.dismiss()
        snackbar = null
    }
}