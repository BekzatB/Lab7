package com.example.lab7.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "jobs_table")
data class JobsData(
    @PrimaryKey
    @SerializedName("id") val jobId: String,
    @SerializedName("title") val jobTitle: String? = null,
    @SerializedName("created_at") val jobCreatedAt: String? = null,
    @SerializedName("type") val jobType: String? = null,
    @SerializedName("company") val jobCompany: String? = null,
    @SerializedName("url") val jobUrl: String? = null,
    @SerializedName("company_url") val JobCompanyUrl: String? = null,
    @SerializedName("location") val jobLocation: String? = null,
    @SerializedName("description") val jobDescription: String? = null,
    @SerializedName("how_to_apply") val jobHowToApply: String? = null,
    @SerializedName("company_logo") val jobCompanyLogo: String? = null,
    var savedJob: Boolean = false
)
