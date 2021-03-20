package com.example.mtctrial.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    companion object {
        var BaseUrl = "http://trials.mtcmobile.co.uk/api/football/1.0/"
    }

    val weatherLiveData = MutableLiveData<String>()

    init {
        val retrofit = Retrofit.Builder().baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(APIClient::class.java)
        val call = service.search("barc")

        call.enqueue(object : Callback<ApiResponseWrapper> {
            override fun onResponse(call: Call<ApiResponseWrapper>, response: Response<ApiResponseWrapper>) {
                if (response.code() == 200) {
                    val responseBody = response.body()!!

                    weatherLiveData.postValue("worked!")
                }
            }

            override fun onFailure(call: Call<ApiResponseWrapper>, t: Throwable) {
                weatherLiveData.postValue(t.message)
            }
        })
    }
}