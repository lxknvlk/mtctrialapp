package com.example.mtctrial.data.api

import com.example.mtctrial.data.model.ApiResponseWrapper
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("search")
    suspend fun search(@Query("searchString") searchString: String): Call<ApiResponseWrapper>
}