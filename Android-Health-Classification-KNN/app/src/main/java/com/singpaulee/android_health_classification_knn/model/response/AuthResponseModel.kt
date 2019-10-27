package com.singpaulee.android_health_classification_knn.model.response

import com.google.gson.annotations.SerializedName
import com.singpaulee.android_health_classification_knn.model.base.AuthModel
import com.singpaulee.android_health_classification_knn.model.base.StatusModel

data class AuthResponseModel (

    @field:SerializedName("status")
    val status: StatusModel? = null,

    @field:SerializedName("result")
    val auth: AuthModel? = null

)