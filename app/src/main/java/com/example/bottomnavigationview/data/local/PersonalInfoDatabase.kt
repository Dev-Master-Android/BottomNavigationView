package com.example.bottomnavigationview.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bottomnavigationview.data.model.PersonalInfo

@Database(entities = [PersonalInfo::class], version = 1)
abstract class PersonalInfoDatabase : RoomDatabase() {
    abstract fun personalInfoDao(): PersonalInfoDao

    companion object {
        @Volatile
        private var INSTANCE: PersonalInfoDatabase? = null

        fun getDatabase(context: Context): PersonalInfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonalInfoDatabase::class.java,
                    "personal_info_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
