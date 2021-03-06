package ru.wearemad.mad_base.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle

open class EmptyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        //do nothing
    }

    override fun onActivityStarted(activity: Activity) {
        //do nothing
    }

    override fun onActivityResumed(activity: Activity) {
        //do nothing
    }

    override fun onActivityPaused(activity: Activity) {
        //do nothing
    }

    override fun onActivityStopped(activity: Activity) {
        //do nothing
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        //do nothing
    }

    override fun onActivityDestroyed(activity: Activity) {
        //do nothing
    }
}