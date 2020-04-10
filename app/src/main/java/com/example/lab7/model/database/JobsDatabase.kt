package com.example.lab7.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab7.model.data.JobsData

@Database(entities = [JobsData::class], version = 3, exportSchema = false)
abstract class JobsDatabase : RoomDatabase() {
    abstract fun jobsDao(): JobsDao

    companion object {
        @Volatile
        private var INSTANCE: JobsDatabase? = null

        fun getDatabase(context: Context): JobsDatabase? {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    JobsDatabase::class.java,
                    "jobs_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}