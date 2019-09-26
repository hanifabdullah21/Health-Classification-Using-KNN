package com.singpaulee.android_health_classification_knn.model.base

import com.google.gson.annotations.SerializedName

data class StatusClassificationModel(

    @field:SerializedName("status")
    val status : String? = null,

    @field:SerializedName("total")
    var total : Int? = null
)