package ru.wearemad.base.message

import android.app.Activity
import ru.wearemad.base.activity.EmptyActivityLifecycleCallbacks

class MessageControllerActivityCallback(
    private val messageController: MessageController
) : EmptyActivityLifecycleCallbacks() {

    override fun onActivityPaused(activity: Activity) {
        messageController.cancelAll()
    }
}