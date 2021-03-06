package ru.wearemad.mad_base.message

import android.app.Activity
import ru.wearemad.mad_base.activity.EmptyActivityLifecycleCallbacks

class MessageControllerActivityCallback(
    private val messageController: MessageController
) : EmptyActivityLifecycleCallbacks() {

    override fun onActivityPaused(activity: Activity) {
        messageController.cancelAll()
    }
}