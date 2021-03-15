package ru.wearemad.mad_base.common_ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

fun View.roundTopCorners(radiusDp: Int) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = radiusDp.dpToPx
            outline.setRoundRect(
                0,
                0,
                view.measuredWidth,
                view.measuredHeight + radius,
                radius.toFloat()
            )
        }
    }
    clipToOutline = true
}

fun View.roundAllCorners(radiusDp: Int) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = radiusDp.dpToPx
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, radius.toFloat())
        }
    }
    clipToOutline = true
}

fun View.resColor(@ColorRes color: Int): Int = context.resColor(color)

fun View.resColorState(@ColorRes color: Int): ColorStateList = context.resColorState(color)

fun View.setRoundedBgWithColor(
    @ColorRes color: Int,
    cornersDp: Int
) {
    background = MaterialShapeDrawable(
        ShapeAppearanceModel
            .builder()
            .setAllCornerSizes(cornersDp.dpToPx.toFloat())
            .build()
    ).apply {
        fillColor = ColorStateList.valueOf(resColor(color))
    }
}

fun View.hideKeyboard() {
    val token = windowToken ?: return
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.hideSoftInputFromWindow(token, 0)
}

fun View.showKeyboard() {
    requestFocus()
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun <V : View, D> V.actionIfChanged(data: D?, action: V.(data: D?) -> Unit) {
    if (updateTag(data)) {
        action(data)
    }
}

fun <V : View, D> V.updateTag(data: D?): Boolean {
    val hash = data?.hashCode() ?: 0
    return if (tag != hash) {
        tag = hash
        true
    } else {
        false
    }
}