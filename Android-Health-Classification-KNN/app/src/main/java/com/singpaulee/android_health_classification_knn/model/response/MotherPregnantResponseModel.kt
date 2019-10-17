package com.singpaulee.android_health_classification_knn.model.response

import com.google.gson.annotations.SerializedName
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.StatusModel

data class MotherPregnantResponseModel(

    @field:SerializedName("status")
    val statusModel: StatusModel? = null,

    @field:SerializedName("result")
    val result: MotherPregnantModel? = null
)

data class MotherPregnantListResponseModel(
    @field:SerializedName("status")
    val statusModel: StatusModel? = null,

    @field:SerializedName("result")
    val result: ArrayList<MotherPregnantModel>? = null
)