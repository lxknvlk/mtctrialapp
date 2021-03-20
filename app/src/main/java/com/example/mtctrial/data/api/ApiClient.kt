package com.example.mtctrial.data.api

import com.example.mtctrial.data.model.ApiResponseWrapper
import com.example.mtctrial.data.model.SearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        var BaseUrl = "http://trials.mtcmobile.co.uk/api/football/1.0/"
    }

    private val apiInterface: ApiInterface by lazy {
        val retrofit = Retrofit.Builder().baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiInterface::class.java)
    }

    suspend fun search(searchString: String): SearchResponse? {
        return withContext(Dispatchers.IO) {
            val call = apiInterface.search(searchString)
            val response = call.execute()

            if (response.code() == 200) {
                val apiResponseWrapper: ApiResponseWrapper? = response.body()
                apiResponseWrapper?.result
            } else {
                null
            }
        }
    }
}