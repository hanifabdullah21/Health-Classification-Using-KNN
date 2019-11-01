package com.singpaulee.android_health_classification_knn.model.response

import com.google.gson.annotations.SerializedName
import com.singpaulee.android_health_classification_knn.model.base.StatusModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel

data class VillageResponseModel(

    @field:SerializedName("status")
    val statusModel: StatusModel? = null,

    @field:SerializedName("result")
    val result: List<VillageModel>? = null
)

data class VillageResponseObjectModel(
    @field:SerializedName("status")
    val statusModel: StatusModel? = null,

    @field:SerializedName("result")
    val result: VillageModel? = null
)