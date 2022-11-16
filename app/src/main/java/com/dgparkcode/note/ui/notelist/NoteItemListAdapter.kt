package com.dgparkcode.note.ui.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dgparkcode.note.databinding.ItemNoteListBinding

class NoteItemListAdapter(
    private val onItemClick: (item: NoteItem) -> Unit = {},
    private val onDeleteClick: (item: NoteItem) -> Unit = {}
) : ListAdapter<NoteItem, NoteItemListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick,
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }

    class ViewHolder(
        private val binding: ItemNoteListBinding,
        private val onItemClick: (item: NoteItem) -> Unit,
        private val onDeleteClick: (item: NoteItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.content
            binding.root.setOnClickListener { onItemClick(item) }
            binding.btRemove.setOnClickListener { onDeleteClick(item) }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NoteItem>() {
            override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.title == newItem.title &&
                        oldItem.content == newItem.content
            }
        }
    }
}