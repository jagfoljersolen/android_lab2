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

    fun loadAllByIds(phoneIds: IntArray) {
        PhoneDatabase.databaseWriteExecutor.execute {
            phoneDao.loadAllByIds(phoneIds)
        }
    }

    fun findByName(producer: String, model: String) {
        PhoneDatabase.databaseWriteExecutor.execute {
            phoneDao.findByName(producer, model)
        }
    }
}