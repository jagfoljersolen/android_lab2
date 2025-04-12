package com.example.lab2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [Phone::class], version = 1, exportSchema = false)
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
                    INSTANCE?.phoneDao()?.insert(Phone(1, "Samsung", "Galaxy A20","14", "www.samsung.com"),
                        Phone(2, "Samsung", "Galaxy S24", "13", "www.samsung.com"),
                        Phone(3, "Google", "Pixel 6", "14", "www.google.com"),
                        Phone(4, "Sony", "Xperia 5 II", "12", "www.sony.com"),
                        Phone(5, "Nokia", "8.3 5G", "12", "www.nokia.com"),
                        Phone(6, "Huawei", "P50 Pro", "11", "www.huawei.com"),
                        Phone(7, "Xiaomi", "Mi 11 Ultra", "12", "www.mi.com"),
                        Phone(8, "Motorola", "Edge 20 Pro", "13", "www.motorola.com"))

                }
            }
        }


    }
}
