package com.example.bottomnavigationview.data.repository

import androidx.lifecycle.LiveData
import com.example.bottomnavigationview.data.local.NoteDao
import com.example.bottomnavigationview.data.model.Note

class NotesRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}
