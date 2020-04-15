package com.example.lab7.ui.jobs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab7.model.data.JobsData
import com.example.lab7.repository.JobsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class FragmentJobsViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val uiScope: CoroutineScope = CoroutineScope(coroutineContext)

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    private var jobsRepository: JobsRepository = JobsRepository(application)

    fun searchByDescription(description: String) {
        uiScope.launch {
            val response = jobsRepository.searchJobByDescription(description)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    _liveData.postValue(
                        State.JobByDescription(
                            result as ArrayList<JobsData>
                        )
                    )
                }
            }
        }
    }

    fun searchByLocation(location: String) {
        uiScope.launch {
            val response = jobsRepository.searchJobByLocation(location)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    _liveData.postValue(
                        State.JobByLocation(
                            result as ArrayList<JobsData>
                        )
                    )
                }
            }
        }
    }

    fun searchJobs(description: String, location: String) {
        uiScope.launch {
            val response = jobsRepository.searchJobs(description, location)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    _liveData.postValue(
                        State.JobByBoth(
                            result as ArrayList<JobsData>
                        )
                    )
                }
            }
        }
    }

    fun getJobsList(page: Int = 1) {

        uiScope.launch {
            _liveData.value = State.ShowLoading
            val list = withContext(Dispatchers.IO) {
                try {
                    val response = jobsRepository.getAllJobs(page)
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (!result.isNullOrEmpty()) {
                            jobsRepository.addJobsList(result as ArrayList<JobsData>)
                            result.forEach {
                                if (it.savedJob == null) {
                                    jobsRepository.makeFalse("false", it.jobId)
                                }
                            }
                        }
                        result
                    } else {
                        jobsRepository.getAllJobsList() ?: emptyList<JobsData>()
                    }
                } catch (e: Exception) {
                    jobsRepository.getAllJobsList() ?: emptyList<JobsData>()
                }
            }
            _liveData.value = State.HideLoading
            _liveData.value = State.Result(list as ArrayList<JobsData>)
        }
    }

    fun getSavedJobs() {
        uiScope.launch {
            withContext(Dispatchers.Default) {
                val response = jobsRepository.getSavedJobsList()
                if (response != null) {
                    _liveData.postValue(
                        State.SavedJob(
                            result = response as ArrayList<JobsData>
                        )
                    )
                }
            }
        }
    }

    fun setSavedJobs(check: String, jobId: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                jobsRepository.updateSavedJobs(check, jobId)
            }
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class SavedJob(val result: ArrayList<JobsData>) : State()
        data class Result(val jobsList: ArrayList<JobsData>) : State()
        data class JobByDescription(val job: ArrayList<JobsData>) : State()
        data class JobByLocation(val job: ArrayList<JobsData>) : State()
        data class JobByBoth(val job: ArrayList<JobsData>) : State()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}