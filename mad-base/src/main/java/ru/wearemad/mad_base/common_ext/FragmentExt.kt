package ru.wearemad.mad_base.common_ext

import android.view.View
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun Fragment.resColor(@ColorRes color: Int): Int = requireContext().resColor(color)

fun Fragment.hideKeyboard() {
    view?.hideKeyboard()
}

fun BottomSheetDialogFragment.setTopRoundedBgWithColor(
    @ColorRes color: Int,
    cornersDp: Int
) {
    val parentView = view?.parent as? View ?: return
    parentView.post {
        parentView.roundTopCorners(cornersDp)
        parentView.setBackgroundColor(resColor(color))
    }
}