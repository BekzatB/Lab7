package com.example.lab7.model.network

import android.util.Log
import com.example.lab7.model.data.JobsData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object RetrofitService {
    private const val BASE_URL = "https://jobs.github.com"

    fun getJobsApi(): JobsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
        return retrofit.create(
            JobsApi::class.java
        )
    }

    private fun getOkHttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
        return okHttpClient.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

interface JobsApi {
    @GET(" /positions.json?")
    suspend fun getJobsListCoroutine(
        @Query("page") page: Int
    ): Response<List<JobsData>>

    @GET(" /positions/{ID}.json?")
    suspend fun getJobById(
        @Path("ID") jobId: String
    ): Response<JobsData>

    @GET("positions.json?")
    suspend fun searchJobsByDescription(
        @Query("description") description: String
    ): Response<List<JobsData>>

    @GET("positions.json?")
    suspend fun searchJobsByLocation(
        @Query("location") location: String
    ): Response<List<JobsData>>

    @GET("positions.json?")
    suspend fun searchJobs(
        @Query("description") description: String,
        @Query("location") location: String
    ): Response<List<JobsData>>

}