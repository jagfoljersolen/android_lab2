package com.example.lab2

import android.app.Application
import androidx.lifecycle.LiveData

class PhoneRepository(application: Application){
    private val phoneDao: PhoneDao
    private val allPhones: LiveData<List<Phone>>

    init {
        val phoneDatabase = PhoneDatabase.getDatabase(application)
        phoneDao = phoneDatabase.phoneDao()

        allPhones = phoneDao.getAll()
    }

    fun getAllPhones(): LiveData<List<Phone>> {
        return allPhones
    }

    fun deleteAll() {
        PhoneDatabase.databaseWriteExecutor.execute {
            phoneDao.deleteAll()
        }
    }
    fun delete(phone: Phone) {
        PhoneDatabase.databaseWriteExecutor.execute {
            phoneDao.delete(phone)
        }
    }

    fun insert(phone: Phone){
        PhoneDatabase.databaseWriteExecutor.execute {
            phoneDao.insert(phone)
        }
    }


    fun update(phone: Phone) {
        PhoneDatabase.databaseWriteExecutor.execute {
            phoneDao.update(phone)
        }
    }
}