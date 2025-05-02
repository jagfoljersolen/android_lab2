package com.example.lab2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PhoneViewModel(application: Application) : AndroidViewModel(application){
    private val repository: PhoneRepository
    val allPhones: LiveData<List<Phone>>

    init {
        PhoneDatabase.getDatabase(application).phoneDao()
        repository = PhoneRepository(application)
        allPhones = repository.getAllPhones()
    }


    fun deleteAll() {
        repository.deleteAll()
    }

    fun delete(phone: Phone) {
        repository.delete(phone)
    }

    fun insert(phone: Phone) {
        repository.insert(phone)
    }

    fun update(phone: Phone) {
        repository.update(phone)
    }
}