package ru.wearemad.mad_base.message.factory

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.wearemad.mad_base.activity.CurrentActivityHolder

interface SnackbarFactory {

    fun createSnackbar(
        message: String,
        backgroundColor: Int?,
        actionStringId: Int?,
        buttonColor: Int?,
        duration: Int,
        listener: (view: View) -> Unit
    ): Snackbar?
}

class DefaultSnackbarFactory(
    private val activityHolder: CurrentActivityHolder
) : SnackbarFactory {

    override fun createSnackbar(
        message: String,
        backgroundColor: Int?,
        actionStringId: Int?,
        buttonColor: Int?,
        duration: Int,
        listener: (view: View) -> Unit
    ): Snackbar? {
        val rootView = activityHolder
            .currentActivity
            ?.findViewById<View>(android.R.id.content) ?: return null
        return Snackbar.make(rootView, message, duration).apply {
            if (backgroundColor != null) {
                view.setBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))
            }
            if (actionStringId != null) {
                setAction(actionStringId) { view -> listener.invoke(view) }
            }
            if (buttonColor != null) {
                setActionTextColor(ContextCompat.getColor(view.context, buttonColor))
            }
        }
    }
}