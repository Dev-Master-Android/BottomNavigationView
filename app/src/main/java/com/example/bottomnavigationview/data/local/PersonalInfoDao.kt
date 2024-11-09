package com.example.bottomnavigationview.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bottomnavigationview.data.model.PersonalInfo
@Dao
interface PersonalInfoDao {
    @Query("SELECT * FROM personal_info WHERE id = 0")
    fun getPersonalInfo(): LiveData<PersonalInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personalInfo: PersonalInfo)

    @Update
    suspend fun update(personalInfo: PersonalInfo)

    @Query("DELETE FROM personal_info WHERE id = 0")
    suspend fun clear()
}
