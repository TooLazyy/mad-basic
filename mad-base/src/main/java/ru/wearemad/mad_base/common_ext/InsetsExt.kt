package ru.wearemad.mad_base.common_ext

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnAttach

fun View.doOnApplyWindowInsets(listener: (View, WindowInsetsCompat) -> WindowInsetsCompat) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        listener(view, insets)
    }
    requestApplyInsetsWhenAttached()
}

fun View.clearInsetsListener() {
    ViewCompat.setOnApplyWindowInsetsListener(this, null)
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        doOnAttach {
            requestApplyInsets()
        }
    }
}

fun WindowInsetsCompat.updateSystemWindowInsets(
    left: Int = systemWindowInsets.left,
    top: Int = systemWindowInsets.top,
    right: Int = systemWindowInsets.right,
    bottom: Int = systemWindowInsets.bottom
): WindowInsetsCompat {
    return WindowInsetsCompat
        .Builder(this)
        .setSystemWindowInsets(
            Insets.of(left, top, right, bottom)
        )
        .build()
}

fun View.rememberBottomInsetsType(insets: WindowInsetsCompat) {
    if (tag == null) {
        tag = BottomInsetsType.BottomNav(insets.systemWindowInsetBottom)
    } else {
        val currentType = tag as BottomInsetsType
        tag = when (currentType) {
            is BottomInsetsType.BottomNav -> if (currentType.value < insets.systemWindowInsetBottom) {
                BottomInsetsType.Keyboard(insets.systemWindowInsetBottom)
            } else {
                currentType.copy(value = insets.systemWindowInsetBottom)
            }
            is BottomInsetsType.Keyboard -> if (currentType.value > insets.systemWindowInsetBottom) {
                BottomInsetsType.BottomNav(insets.systemWindowInsetBottom)
            } else {
                currentType.copy(value = insets.systemWindowInsetBottom)
            }
        }
    }
}

sealed class BottomInsetsType(
    open val value: Int
) {

    data class BottomNav(override val value: Int) : BottomInsetsType(value)

    data class Keyboard(override val value: Int) : BottomInsetsType(value)
}