package com.hansilk.two.blocks.uploads.uploadsList

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansilk.two.blocks.uploads.data.Upload
import androidx.lifecycle.ViewModelStoreOwner

import androidx.lifecycle.ViewModelProvider
import com.hansilk.two.blocks.uploads.main.UploadsViewModel


internal class UploadListAdapter(uploadDiffCallback: UploadDiffUtil) :

    ListAdapter<Upload, ListViewHolder>(uploadDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    fun onItemMove(viewHolder: RecyclerView.ViewHolder, AdapterPosition: Int) {

    }

    fun onItemDismiss(viewHolder: RecyclerView.ViewHolder) {
        val upload = currentList[viewHolder.adapterPosition]
        upload.state = 2
        ViewModelProvider((viewHolder.itemView.context as ViewModelStoreOwner))
            .get(UploadsViewModel::class.java)
            .updateUpload(upload)
    }

    fun onItemTouchup(context: Context) {

    }

}