package com.example.mtctrial.data.api

import com.example.mtctrial.data.api.model.ApiResponseWrapper
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("search")
    suspend fun search(@Query("searchString") searchString: String): ApiResponseWrapper
}