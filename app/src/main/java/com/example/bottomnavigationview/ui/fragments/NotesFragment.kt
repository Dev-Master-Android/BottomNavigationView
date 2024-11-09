package com.example.bottomnavigationview.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.data.model.Note
import com.example.bottomnavigationview.databinding.FragmentNotesBinding
import com.example.bottomnavigationview.viewmodel.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var noteViewModel: NotesViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        adapter = NoteAdapter(
            onNoteClick = { note, action -> handleNoteAction(note, action) },
            onCheckboxChecked = { note, isChecked -> handleCheckboxState(note, isChecked) })

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes?.let { adapter.submitList(it) }
        }

        binding.addButton.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_note, null)
        val titleEditText: EditText = dialogView.findViewById(R.id.noteTitleEditText)
        val contentEditText: EditText = dialogView.findViewById(R.id.noteContentEditText)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить заметку")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val newNote = Note(
                        id = 0,
                        title = title,
                        content = content,
                        isCompleted = false
                    )
                    noteViewModel.insert(newNote)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showEditDeleteDialog(note: Note) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_note, null)
        val titleEditText: EditText = dialogView.findViewById(R.id.noteTitleEditText)
        val contentEditText: EditText = dialogView.findViewById(R.id.noteContentEditText)

        titleEditText.setText(note.title)
        contentEditText.setText(note.content)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать заметку")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val updatedTitle = titleEditText.text.toString()
                val updatedContent = contentEditText.text.toString()
                if (updatedTitle.isNotEmpty() && updatedContent.isNotEmpty()) {
                    val updatedNote = note.copy(
                        title = updatedTitle,
                        content = updatedContent
                    )
                    noteViewModel.update(updatedNote)
                }
            }
            .setNegativeButton("Удалить") { _, _ ->
                noteViewModel.delete(note)
            }
            .setNeutralButton("Отмена", null)
            .show()
    }

    private fun handleNoteAction(note: Note, action: Any?) {
        when (action) {
            true -> showEditDeleteDialog(note)
            false -> noteViewModel.delete(note)
        }
    }

    private fun handleCheckboxState(note: Note, isChecked: Boolean) {
        noteViewModel.setTaskCompletion(note, isChecked)
    }
}
