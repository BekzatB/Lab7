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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
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

        srlJobs.setOnRefreshListener {
            jobsRecyclerViewAdapter.clear()
            jobsFragmentViewModel.getJobsList()
        }
}

    private fun setAdapter() {
        jobsRecyclerView.adapter = jobsRecyclerViewAdapter

        jobsRecyclerViewAdapter.setOnItemClickListener(onItemClickListener = object  :
        OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                navController.navigate(R.id.action_fragmentJobs_to_fragmentsDetails)
            }

            override fun onHeartClick(position: Int, savedJob: CheckBox) {
            }
        })
    }

    private fun setData() {
       observe()
    }

    private fun observe() {
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