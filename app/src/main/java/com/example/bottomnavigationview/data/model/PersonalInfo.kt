package com.example.bottomnavigationview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_info")
data class PersonalInfo(
    @PrimaryKey val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val address: String
)
