package com.brainberry.foxdigital

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var currentPosition: Int = 0

    protected abstract fun clear()

    open fun onBind(position: Int) {
        currentPosition = position
        clear()
    }

}
