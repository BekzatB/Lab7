package com.example.lab7.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "jobs_table")
data class JobsData(

    @SerializedName("id") val jobId: String? = null,
    @SerializedName("title") val jobTitle: String? = null,
    @SerializedName("created_at") val jobCreatedAt: String? = null,
    @SerializedName("type") val jobType: String? = null,
    @SerializedName("company") val jobCompany: String? = null,
    @SerializedName("location") val jobLocation: String? = null,
    @SerializedName("description") val jobDescription: String? = null,
    @SerializedName("how_to_apply") val jobHowToApply: String? = null,
    @SerializedName("company_logo") val jobCompanyLogo: String? = null,
    val savedJob: Boolean? = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null
)
