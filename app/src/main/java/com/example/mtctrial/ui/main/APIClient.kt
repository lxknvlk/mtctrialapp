package com.example.mtctrial.ui.main

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface APIClient {
    @POST("search")
    fun search(@Query("searchString") searchString: String): Call<ApiResponseWrapper>
}