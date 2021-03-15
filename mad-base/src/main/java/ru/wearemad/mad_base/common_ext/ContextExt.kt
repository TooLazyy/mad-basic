package ru.wearemad.mad_base.common_ext

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Context.resColor(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.resColorState(@ColorRes color: Int): ColorStateList =
    AppCompatResources.getColorStateList(this, color)