package ru.wearemad.mad_base.common_ext

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity

inline fun Activity.finishIfNotRoot(action: () -> Unit) {
    if (!isTaskRoot) {
        val mIntent = intent
        if (mIntent.isMainLauncherIntent) {
            finish()
            action()
        }
    }
}

fun AppCompatActivity.initInsets(
    @ColorInt
    statusBarColor: Int,
    @ColorInt
    navigationBarColor: Int
) {
    window.apply {
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decorView.systemUiVisibility =
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        this.statusBarColor = statusBarColor
        this.navigationBarColor = navigationBarColor
    }
}

val Activity.rootView: View
    get() = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)

val Intent.isMainLauncherIntent: Boolean
    get() {
        val intentAction = action
        return hasCategory(Intent.CATEGORY_LAUNCHER) &&
                intentAction != null &&
                intentAction == Intent.ACTION_MAIN
    }