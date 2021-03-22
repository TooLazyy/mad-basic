package ru.wearemad.mad_base.message.factory

import android.widget.Toast
import ru.wearemad.mad_base.activity.CurrentActivityHolder

interface ToastFactory {

    fun createToast(
        text: String,
        duration: Int
    ): Toast?
}

class DefaultToastFactory(
    private val activityHolder: CurrentActivityHolder,
) : ToastFactory {

    override fun createToast(text: String, duration: Int): Toast? {
        val activity = activityHolder.currentActivity ?: return null
        return Toast.makeText(activity, text, duration)
    }
}