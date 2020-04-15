package com.example.lab7.ui.saved_jobs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab7.model.data.JobsData
import com.example.lab7.repository.JobsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class FragmentSavedJobsViewModel(application: Application) : AndroidViewModel(application) {

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    private var jobsRepository: JobsRepository = JobsRepository(application)

     fun getSavedJobs() {
        _liveData.value = State.SavedJob(
            jobsRepository.getSavedJobsList() as ArrayList<JobsData>
        )
    }

     fun updateSavedJobs(check: String, jobId: String) {
        jobsRepository.updateSavedJobs(check, jobId)
    }

    sealed class State {
        data class SavedJob(val result: ArrayList<JobsData>) : State()
    }

}