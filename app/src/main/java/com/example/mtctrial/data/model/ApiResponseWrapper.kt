package com.example.mtctrial.data.model

import com.example.mtctrial.data.model.SearchResponse
import com.google.gson.annotations.SerializedName

class ApiResponseWrapper {
    @SerializedName("result")
    var result: SearchResponse? = null
}