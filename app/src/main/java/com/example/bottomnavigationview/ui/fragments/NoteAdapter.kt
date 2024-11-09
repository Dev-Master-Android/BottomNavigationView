package com.example.bottomnavigationview.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.data.model.Note

class NoteAdapter(private val onNoteClick: (Note, Any?) -> Unit, private val onCheckboxChecked: (Note, Boolean) -> Unit) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.noteCheckbox)
        private val title: TextView = itemView.findViewById(R.id.noteTitle)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = getItem(position)
                    onNoteClick(note, true)
                }
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = getItem(position)
                    onCheckboxChecked(note, isChecked)
                }
            }

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = getItem(position)
                    onNoteClick(note, false)
                }
            }
        }

        fun bind(note: Note) {
            checkBox.isChecked = note.isCompleted
            title.text = note.title
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
