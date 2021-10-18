package com.hansilk.two.blocks.uploads.uploadsList

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hansilk.two.blocks.uploads.data.Upload

internal class UploadDiffUtil : DiffUtil.ItemCallback<Upload>() {
    override fun areItemsTheSame(oldItem: Upload, newItem: Upload): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Upload, newItem: Upload): Boolean {
        return oldItem == newItem
    }
}