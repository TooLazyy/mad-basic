package ru.wearemad.mad_base.activity

import android.app.Activity
import android.os.Bundle

class CurrentActivityHolderActivityCallback(
    private val activityHolder: CurrentActivityHolder
) : EmptyActivityLifecycleCallbacks() {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activityHolder.currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        activityHolder.currentActivity = null
    }

    override fun onActivityResumed(activity: Activity) {
        activityHolder.currentActivity = activity
    }
}