package com.example.backtolife.models

import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("success")
    var success = false

    @SerializedName("message")
    var message: String? = null
}