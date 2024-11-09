package com.example.bottomnavigationview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigationview.data.local.NoteDatabase
import com.example.bottomnavigationview.data.model.Note
import com.example.bottomnavigationview.data.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepository
    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NotesRepository(noteDao)
        allNotes = repository.allNotes
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun setTaskCompletion(note: Note, isCompleted: Boolean) = viewModelScope.launch {
        val updatedNote = note.copy(isCompleted = isCompleted)
        repository.update(updatedNote)
    }
}
