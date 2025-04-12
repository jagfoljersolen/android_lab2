package com.example.lab2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Phone(
    @PrimaryKey val id: Int,
    @ColumnInfo val producer: String,
    @ColumnInfo val model: String,
    @ColumnInfo val androidVersion: String,
    @ColumnInfo val webpage: String
)
