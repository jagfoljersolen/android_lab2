package com.example.lab2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Phone(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo var producer: String,
    @ColumnInfo var model: String,
    @ColumnInfo var androidVersion: String,
    @ColumnInfo var webpage: String
)
