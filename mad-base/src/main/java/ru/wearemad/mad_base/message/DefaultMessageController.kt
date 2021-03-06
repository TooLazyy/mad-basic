package ru.wearemad.mad_base.message

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.wearemad.mad_base.activity.CurrentActivityHolder

class DefaultMessageController(
    private val activityHolder: CurrentActivityHolder
) : MessageController {

    private var toast: Toast? = null
    private var snackbar: Snackbar? = null

    override fun showToast(text: String, duration: Int) {
        cancelToast()
        activityHolder.currentActivity?.let {
            toast = Toast.makeText(it, text, duration)
            toast?.show()
        }
    }

    override fun showToast(textResId: Int, duration: Int) {
        cancelToast()
        activityHolder.currentActivity?.let {
            toast = Toast.makeText(it, it.getString(textResId), duration)
            toast?.show()
        }
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
        val rootView =
            activityHolder.currentActivity?.findViewById<View>(android.R.id.content) ?: return
        snackbar = Snackbar.make(rootView, message, duration).apply {
            if (backgroundColor != null) {
                view.setBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))
            }
            if (actionStringId != null) {
                setAction(actionStringId) { view -> listener.invoke(view) }
            }
            if (buttonColor != null) {
                setActionTextColor(ContextCompat.getColor(view.context, buttonColor))
            }
            show()
        }
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