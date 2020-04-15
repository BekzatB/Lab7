package com.example.lab7.ui.saved_jobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab7.R
import com.example.lab7.base.BaseRecyclerViewAdapter
import com.example.lab7.model.data.JobsData

class SavedJobsAdapter : BaseRecyclerViewAdapter<JobsData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SavedJobsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.jobs_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as SavedJobsViewHolder
        getItem(position)?.let { myHolder.bind(jobsData = it) }
    }

    inner class SavedJobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val jobCompanyLogo = itemView.findViewById<ImageView>(R.id.jobCompanyLogo)
        private val jobTitle = itemView.findViewById<TextView>(R.id.jobTitle)
        private val jobType = itemView.findViewById<TextView>(R.id.jobType)
        private val jobCreatedAt = itemView.findViewById<TextView>(R.id.jobCreatedAt)
        private val savedJob = itemView.findViewById<CheckBox>(R.id.savedJob)

        init {
            savedJob.setOnClickListener(this)
            jobCompanyLogo.setOnClickListener(this)
            jobTitle.setOnClickListener(this)
            jobType.setOnClickListener(this)
            jobCreatedAt.setOnClickListener(this)
        }

        fun bind(jobsData: JobsData) {
            Glide.with(itemView)
                .load(jobsData.jobCompanyLogo)
                .into(jobCompanyLogo)

            jobTitle.text = jobsData.jobTitle
            jobType.text = jobsData.jobType
            jobCreatedAt.text = jobsData.jobCreatedAt
        }

            override fun onClick(v: View?) {
                if (v != null) {
                    if (v.id == savedJob.id) {
                        itemClickListener?.onHeartClick(adapterPosition, v)
                    } else {
                        itemClickListener?.onItemClick(adapterPosition, v)
                    }
                }
            }
        }

}