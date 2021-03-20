package com.example.mtctrial.ui.main

import com.google.gson.annotations.SerializedName

class ApiResponseWrapper {
    @SerializedName("result")
    var result: SearchResponse? = null
}