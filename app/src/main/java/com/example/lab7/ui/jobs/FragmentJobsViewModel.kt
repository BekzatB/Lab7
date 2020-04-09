package com.example.lab7.ui.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab7.base.BaseViewModel
import com.example.lab7.model.data.JobsData
import com.example.lab7.model.network.RetrofitService
import kotlinx.coroutines.launch

open class FragmentJobsViewModel : BaseViewModel() {


    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData


    init {

        //  Log.d("123", RetrofitService.getJobsApi().getJobsListCoroutine(1).isSuccessful.toString())
        getJobsList()
    }

    private fun getJobsList(page: Int = 1) {
        _liveData.value = State.ShowLoading
        uiScope.launch {
            val response = RetrofitService.getJobsApi().getJobsListCoroutine(page)
            if (response.isSuccessful) {
                val result = response.body()
                _liveData.postValue(
                    State.Result(
                        result as ArrayList<JobsData>
                    )
                )
            }
        }
        _liveData.value = State.HideLoading
    }


    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val jobsList: ArrayList<JobsData>) : State()
    }
}