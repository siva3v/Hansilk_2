package com.hansilk.two.support.utilsPrime.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

internal class ModelDiffUtil : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        //return oldItem.id == newItem.id
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.toString() == newItem.toString()
    }
}