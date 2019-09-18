package com.singpaulee.android_health_classification_knn.model.base

import com.google.gson.annotations.SerializedName

data class StatusModel(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null

)