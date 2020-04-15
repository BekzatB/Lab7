package com.example.lab7.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab7.model.data.JobsData
import com.example.lab7.repository.JobsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FragmentDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val uiScope: CoroutineScope = CoroutineScope(coroutineContext)

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    private var jobsRepository: JobsRepository = JobsRepository(application)

    fun getJobById(jobId: String) {
        uiScope.launch {
            _liveData.value = State.ShowLoading
            val response = jobsRepository.getJobById(jobId)
            if (response.isSuccessful) {
                val result = response.body()
                _liveData.postValue(
                    result?.let {
                        State.Result(
                            it
                        )
                    }
                )
            }
            _liveData.value = State.HideLoading
        }
    }

    fun setSavedJobs(jobsData: JobsData) {

    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class SavedJob(val result: List<JobsData>) : State()
        data class Result(val jobsList: JobsData) : State()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}