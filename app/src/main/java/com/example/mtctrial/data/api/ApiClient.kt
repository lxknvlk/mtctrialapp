package com.example.mtctrial.data.api

import com.example.mtctrial.data.api.model.ApiResponseWrapper
import com.example.mtctrial.ui.viewmodel.MainViewModel
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

    suspend fun search(searchString: String, searchType: MainViewModel.SearchType?, offset: Int?): ApiResponseWrapper {
        return try {
            apiInterface.search(searchString, searchType?.toString()?.toLowerCase(), offset)
        } catch (e: Exception) {
            e.printStackTrace()
            val response = ApiResponseWrapper()
            response.error = e.message
            response
        }
    }
}