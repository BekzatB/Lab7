package com.example.lab7.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


open class BaseViewModel: ViewModel() {
    private val job = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected val uiScope: CoroutineScope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}