package ru.wearemad.mad_base.common_ext

import android.view.View
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

val Fragment.isLastFragment: Boolean
    get() {
        var lastFragment = activity
            ?.supportFragmentManager
            ?.findLastFragment()
        if (this == lastFragment) {
            return true
        }
        var parent = this.parentFragment
        while (parent != null && parent.isLastFragment) {
            lastFragment = parent.childFragmentManager.findLastFragment()
            if (this == lastFragment) {
                return true
            }
            parent = parent.parentFragment
        }
        return false
    }

private fun FragmentManager.findLastFragment(): Fragment? =
    fragments.lastOrNull { it::class.java.simpleName != "SupportRequestManagerFragment" }