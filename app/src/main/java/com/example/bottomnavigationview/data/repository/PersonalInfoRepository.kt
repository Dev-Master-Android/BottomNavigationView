package com.example.bottomnavigationview.data.repository

import androidx.lifecycle.LiveData
import com.example.bottomnavigationview.data.local.PersonalInfoDao
import com.example.bottomnavigationview.data.model.PersonalInfo

class PersonalInfoRepository(private val personalInfoDao: PersonalInfoDao) {
    val personalInfo: LiveData<PersonalInfo> = personalInfoDao.getPersonalInfo()

    suspend fun insert(personalInfo: PersonalInfo) {
        personalInfoDao.insert(personalInfo)
    }

    suspend fun update(personalInfo: PersonalInfo) {
        personalInfoDao.update(personalInfo)
    }

    suspend fun clear() {
        personalInfoDao.clear()
    }
}
