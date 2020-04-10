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

    @Query("SELECT jobId FROM jobs_table WHERE savedJob = :check")
    fun getSavedJobs(check : Boolean): List<JobsData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobsData: List<JobsData>)

    @Query("UPDATE jobs_table SET savedJob = :check WHERE jobId = :jobId")
    suspend fun updateSavedJobs(check : Boolean, jobId: String)
}
