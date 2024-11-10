package com.example.bottomnavigationview.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bottomnavigationview.data.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}
