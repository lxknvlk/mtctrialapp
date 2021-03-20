package com.example.mtctrial.data.api.model

import com.google.gson.annotations.SerializedName

class ApiResponseWrapper {
    @SerializedName("result")
    var result: SearchResponse? = null
}