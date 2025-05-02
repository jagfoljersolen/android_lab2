package com.example.lab2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [Phone::class], version = 2, exportSchema = false)
abstract class PhoneDatabase : RoomDatabase() {
    abstract fun phoneDao(): PhoneDao

    companion object {
        @Volatile
        private var INSTANCE: PhoneDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): PhoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhoneDatabase::class.java,
                    "phone_database"
                )
                    .addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private val sRoomDatabaseCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute {
                    INSTANCE?.phoneDao()?.insert(Phone(0, "Samsung", "Galaxy A20","14", "https://www.samsung.com"),
                        Phone(0, "Samsung", "Galaxy S24", "13", "https://www.samsung.com"),
                        Phone(0, "Google", "Pixel 6", "14", "https://www.google.com"),
                        Phone(0, "Sony", "Xperia 5 II", "12", "https://www.sony.com"),
                        Phone(0, "Nokia", "8.3 5G", "12", "https://www.nokia.com"),
                        Phone(0, "Huawei", "P50 Pro", "11", "https://www.huawei.com"),
                        Phone(0, "Xiaomi", "Mi 11 Ultra", "12", "https://www.mi.com"),
                        Phone(0, "Motorola", "Edge 20 Pro", "13", "https://www.motorola.com"))

                }
            }
        }


    }
}
