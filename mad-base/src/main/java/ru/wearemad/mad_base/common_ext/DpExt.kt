package ru.wearemad.mad_base.common_ext

import android.content.res.Resources

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()