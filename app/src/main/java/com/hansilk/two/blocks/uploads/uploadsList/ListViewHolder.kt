package com.hansilk.two.blocks.uploads.uploadsList

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansilk.two.blocks.uploads.data.Upload
import com.hansilk.two.databinding.RvUploadlistItemBinding

internal class ListViewHolder private constructor(var binding: RvUploadlistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(upload: Upload?) {
        binding.tvContent.text = upload?.ab.toString()
    }

    companion object {
        fun create(parent: ViewGroup): ListViewHolder {
            return ListViewHolder(
                RvUploadlistItemBinding.inflate(
                    (parent.context as Activity).layoutInflater,
                    parent,
                    false
                )
            )
        }
    }
}