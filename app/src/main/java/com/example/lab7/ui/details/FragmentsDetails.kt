package com.example.lab7.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.lab7.R

class FragmentsDetails: Fragment() {

    private lateinit var jobCompanyLogo: ImageView
    private lateinit var jobCreatedAt: TextView
    private lateinit var jobCompany: TextView
    private lateinit var jobTitle: TextView
    private lateinit var jobLocation: TextView
    private lateinit var jobType: TextView
    private lateinit var jobDescriptionText: TextView
    private lateinit var jobDescriptionButton: Button
    private lateinit var jobHowToApplyText: TextView
    private lateinit var jobHowToApplyButton: Button
    private lateinit var hearButton: Button
    private lateinit var rootView: View
    private lateinit var jobId: String
    private val fragmentDetailsViewModel: FragmentDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_datails, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setData()
    }

    private fun bindView(view: View) = with(view) {
        jobCompanyLogo = findViewById(R.id.jobCompanyLogo)
        jobCompany = findViewById(R.id.jobCompany)
        jobCreatedAt = findViewById(R.id.jobCreatedAt)
        jobTitle = findViewById(R.id.jobTitle)
        jobLocation = findViewById(R.id.jobLocation)
        jobType = findViewById(R.id.jobType)
        jobDescriptionText = findViewById(R.id.jobDescriptionText)
        jobDescriptionButton = findViewById(R.id.jobDescriptionButton)
        jobHowToApplyText = findViewById(R.id.jobHowToApplyText)
        jobHowToApplyButton = findViewById(R.id.jobHowToApplyButton)
        hearButton = findViewById(R.id.heartButton)
        jobId = arguments?.getString("job_id", "null").toString()

        jobDescriptionButton.setOnClickListener {
            if (jobDescriptionText.isVisible) {
                jobDescriptionText.visibility = View.INVISIBLE
            } else {
                jobDescriptionText.visibility = View.VISIBLE
            }
        }

        jobHowToApplyButton.setOnClickListener {
            if (jobHowToApplyText.isVisible) {
                jobHowToApplyText.visibility = View.INVISIBLE
            } else {
                jobHowToApplyText.visibility = View.VISIBLE
            }
        }
    }

    private fun setData() {
        fragmentDetailsViewModel.getJobById(jobId)

        fragmentDetailsViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is FragmentDetailsViewModel.State.Result -> {
                    Glide.with(rootView)
                        .load(result.jobsList.jobCompanyLogo)
                        .into(jobCompanyLogo)
                    jobCompany.text = result.jobsList.jobCompany
                    jobCreatedAt.text = result.jobsList.jobCreatedAt
                    jobTitle.text = result.jobsList.jobTitle
                    jobLocation.text = result.jobsList.jobTitle
                    jobType.text = result.jobsList.jobType
                    jobDescriptionText.text = result.jobsList.jobDescription
                    jobHowToApplyText.text = result.jobsList.jobHowToApply
                }
            }
        })
    }
}
