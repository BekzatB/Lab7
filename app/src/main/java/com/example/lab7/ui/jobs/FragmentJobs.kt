package com.example.lab7.ui.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lab7.R
import com.example.lab7.model.data.JobsData

class FragmentJobs : Fragment() {

    private lateinit var navController: NavController
    private lateinit var jobsRecyclerViewAdapter: JobsRecyclerViewAdapter
    private lateinit var jobsRecyclerView: RecyclerView
    private lateinit var srlJobs: SwipeRefreshLayout
    private  val  jobsFragmentViewModel: FragmentJobsViewModel by viewModels()
    private val linearLayoutManager = LinearLayoutManager(
        activity,
        LinearLayoutManager.VERTICAL,
        false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()

    }

    private fun onCreateComponent() {
        jobsRecyclerViewAdapter = JobsRecyclerViewAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setAdapter()
        setData()
    }

    private fun bindView(view: View) = with(view) {
        jobsRecyclerView = view.findViewById(R.id.jobsRecyclerView)
        srlJobs = view.findViewById(R.id.srlJobs)
        navController = Navigation.findNavController(this)

        jobsRecyclerView.layoutManager = linearLayoutManager


}

    private fun setAdapter() {
        jobsRecyclerView.adapter = jobsRecyclerViewAdapter
    }

    private fun setData() {
        jobsRecyclerViewAdapter.clear()
        jobsFragmentViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is FragmentJobsViewModel.State.ShowLoading -> {
                    srlJobs.isRefreshing = true
                }
                is FragmentJobsViewModel.State.HideLoading -> {
                    srlJobs.isRefreshing = false
                }
                is FragmentJobsViewModel.State.Result -> {
                    jobsRecyclerViewAdapter.addItems(result.jobsList as ArrayList<JobsData>)
                }
            }
        })
    }
}