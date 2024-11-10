package com.example.bottomnavigationview.ui.fragments

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.data.model.Note
import com.example.bottomnavigationview.databinding.ListItemNoteBinding

class NoteAdapter(
    private val onNoteClick: (Note, Any?) -> Unit,
    private val onCheckboxChecked: (Note, Boolean) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    inner class NoteViewHolder(private val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.expandButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = getItem(position)
                    note.isExpanded = !note.isExpanded
                    itemView.post { notifyItemChanged(position) }
                }
            }

            binding.noteCheckbox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = getItem(position)
                    onCheckboxChecked(note, isChecked)
                    note.isCompleted = isChecked
                    itemView.post { notifyItemChanged(position) }
                }
            }

            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = getItem(position)
                    onNoteClick(note, false)
                }
            }
        }

        fun bind(note: Note) {
            binding.noteCheckbox.isChecked = note.isCompleted
            binding.noteTitle.text = note.title
            binding.noteContent.text = note.content
            binding.noteContent.visibility = if (note.isExpanded) View.VISIBLE else View.GONE
            binding.expandButton.setImageResource(
                if (note.isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
            )

            if (note.isCompleted) {
                binding.noteTitle.paintFlags = binding.noteTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.noteTitle.paintFlags = binding.noteTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            binding.noteTitle.isSelected = true // This line makes the TextView scrollable horizontally
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
