package com.example.lab7.repository

import android.app.Application
import com.example.lab7.model.data.JobsData
import com.example.lab7.model.database.JobsDao
import com.example.lab7.model.database.JobsDatabase
import com.example.lab7.model.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class JobsRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var jobsDao: JobsDao?
    private val retrofit = RetrofitService

    init {
        val db = JobsDatabase.getDatabase(application)
        jobsDao = db?.jobsDao()
    }

    fun getAllJobsList() = jobsDao?.getAllJobs()

    fun getSavedJobsList(check: Boolean = true) = jobsDao?.getSavedJobs(check)

    fun addJobsList(jobsData: List<JobsData>) {
        launch { addTaskBG(jobsData) }
    }

    fun updateSavedJobs(check: Boolean, jobId: String){
        launch {
            updateSavedJobs(check, jobId)
        }
    }
    suspend fun updateJobsBG(check: Boolean, jobId: String) {
        withContext(Dispatchers.IO) {
            jobsDao?.updateSavedJobs(check, jobId)
        }
    }

    private suspend fun addTaskBG(jobsData: List<JobsData>) {
        withContext(Dispatchers.IO) {
            jobsDao?.insert(jobsData)
        }
    }

    suspend fun getAllJobs(page: Int): Response<List<JobsData>> {
        return retrofit.getJobsApi().getJobsListCoroutine(page = page)
    }

}