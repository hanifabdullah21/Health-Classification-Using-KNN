package com.singpaulee.android_health_classification_knn.model.response

import com.google.gson.annotations.SerializedName
import com.singpaulee.android_health_classification_knn.model.base.StatusModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel

data class ToddlerResponseModel(

    @field:SerializedName("status")
    val statusModel: StatusModel? = null,

    @field:SerializedName("result")
    val result: ToddlerModel? = null
)

data class ToddlerListResponseModel(
    @field:SerializedName("status")
    val statusModel: StatusModel? = null,

    @field:SerializedName("result")
    val result: ArrayList<ToddlerModel>? = null
)