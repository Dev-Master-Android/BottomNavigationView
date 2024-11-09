package com.example.bottomnavigationview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigationview.data.local.PersonalInfoDatabase
import com.example.bottomnavigationview.data.model.PersonalInfo
import com.example.bottomnavigationview.data.repository.PersonalInfoRepository
import kotlinx.coroutines.launch

class PersonalInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PersonalInfoRepository
    val personalInfo: LiveData<PersonalInfo>

    init {
        val personalInfoDao = PersonalInfoDatabase.getDatabase(application).personalInfoDao()
        repository = PersonalInfoRepository(personalInfoDao)
        personalInfo = repository.personalInfo
    }

    fun insert(personalInfo: PersonalInfo) = viewModelScope.launch {
        repository.insert(personalInfo)
    }

    fun update(personalInfo: PersonalInfo) = viewModelScope.launch {
        repository.update(personalInfo)
    }

    fun clearPersonalInfo() = viewModelScope.launch {
        repository.clear()
    }
}
