package com.example.lab7.ui.jobs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lab7.R
import com.example.lab7.base.OnItemClickListener
import com.example.lab7.model.data.JobsData

class FragmentJobs : Fragment() {

    private lateinit var navController: NavController
    private lateinit var jobsRecyclerViewAdapter: JobsRecyclerViewAdapter
    private lateinit var jobsRecyclerView: RecyclerView
    private lateinit var srlJobs: SwipeRefreshLayout
    private val jobsFragmentViewModel: FragmentJobsViewModel by viewModels()

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

        jobsRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        srlJobs.setOnRefreshListener {
            jobsRecyclerViewAdapter.clear()
            jobsFragmentViewModel.getJobsList()
        }
    }

    private fun setAdapter() {
        jobsRecyclerView.adapter = jobsRecyclerViewAdapter

        jobsRecyclerViewAdapter.setOnItemClickListener(onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                val bundle = Bundle()
                Log.d("123", jobsRecyclerViewAdapter.getItem(position)?.jobId.toString())
                bundle.putString(
                    "job_id",
                    jobsRecyclerViewAdapter.getItem(position)?.jobId.toString()
                )
                navController.navigate(
                    R.id.action_fragmentJobs_to_fragmentsDetails,
                    bundle
                )
            }

            override fun onHeartClick(position: Int, view: View) {
                val savedJob = view.findViewById<CheckBox>(R.id.savedJob)
                jobsFragmentViewModel.getSavedJobs()
                val jobsData: JobsData? = jobsRecyclerViewAdapter.getItem(position)
                Log.d("123", jobsData?.savedJob.toString())
                jobsFragmentViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is FragmentJobsViewModel.State.SavedJob -> {
                            result.result.forEach {
                                if (it == jobsData) {
                                    savedJob.setBackgroundResource(R.drawable.not_pressed_heart)
                                    jobsFragmentViewModel.setSavedJobs("false", it.jobId)
                                } else {
                                    savedJob.setBackgroundResource(R.drawable.heart_box)
                                    jobsFragmentViewModel.setSavedJobs("true", it.jobId)
                                }
                            }
                        }
                    }
                })
            }
        })
    }

    private fun setData() {
        jobsRecyclerViewAdapter.clear()
        jobsFragmentViewModel.getJobsList()

        jobsFragmentViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is FragmentJobsViewModel.State.ShowLoading -> {
                    srlJobs.isRefreshing = true
                }
                is FragmentJobsViewModel.State.HideLoading -> {
                    srlJobs.isRefreshing = false
                }
                is FragmentJobsViewModel.State.Result -> {
                    jobsRecyclerViewAdapter.addItems(result.jobsList)
                }
            }
        })
    }
}