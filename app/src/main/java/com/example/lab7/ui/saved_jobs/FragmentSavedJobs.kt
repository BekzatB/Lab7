package com.example.lab7.ui.saved_jobs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.R
import com.example.lab7.base.OnItemClickListener

class FragmentSavedJobs : Fragment() {

    private lateinit var savedJobRecyclerView: RecyclerView
    private lateinit var savedJobsAdapter: SavedJobsAdapter
    private val fragmentSavedJobsViewModel: FragmentSavedJobsViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    private fun onCreateComponent() {
        savedJobsAdapter = SavedJobsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setData()
    }

    private fun bindView(view: View) = with(view) {
        savedJobRecyclerView = findViewById(R.id.savedJobRecyclerView)
        navController = Navigation.findNavController(view)
        savedJobRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        savedJobRecyclerView.adapter = savedJobsAdapter

        savedJobsAdapter.setOnItemClickListener(onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                val bundle = Bundle()
                Log.d("123", savedJobsAdapter.getItem(position)?.jobId.toString())
                bundle.putString(
                    "job_id",
                    savedJobsAdapter.getItem(position)?.jobId.toString()
                )
                navController.navigate(
                    R.id.action_fragmentSavedJobs_to_fragmentsDetails,
                    bundle
                )
            }

            override fun onHeartClick(position: Int, view: View) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun setData() {
        fragmentSavedJobsViewModel.getSavedJobs()
        fragmentSavedJobsViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is FragmentSavedJobsViewModel.State.SavedJob -> {
                    savedJobsAdapter.addItems(result.result)
                }
            }
        })
    }
}