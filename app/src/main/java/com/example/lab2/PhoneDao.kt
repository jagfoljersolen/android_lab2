package com.example.lab2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PhoneDao {
    @Query("SELECT * FROM phone")
    fun getAll(): LiveData<List<Phone>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg phones: Phone)

    @Delete
    fun delete(vararg phones: Phone)

    @Query("DELETE FROM phone")
    fun deleteAll()

    @Update
    fun update(phone: Phone)
}