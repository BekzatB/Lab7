package com.example.lab7.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab7.model.data.JobsData

@Dao
interface JobsDao {
    @Query("SELECT * FROM jobs_table")
    fun getAllJobs(): LiveData<List<JobsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobsData: List<JobsData>?)
}