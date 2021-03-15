package ru.wearemad.mad_base.common_ext

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.initVertical(adapter: RecyclerView.Adapter<*>) {
    layoutManager = LinearLayoutManager(context)
    itemAnimator = null
    this.adapter = adapter
    overScrollMode = View.OVER_SCROLL_NEVER
}

fun RecyclerView.initHorizontal(adapter: RecyclerView.Adapter<*>) {
    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    itemAnimator = null
    this.adapter = adapter
    overScrollMode = View.OVER_SCROLL_NEVER
}