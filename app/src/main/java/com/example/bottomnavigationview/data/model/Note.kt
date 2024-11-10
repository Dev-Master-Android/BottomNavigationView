package com.example.bottomnavigationview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    var isCompleted: Boolean = false,
    var isExpanded: Boolean = false
)
