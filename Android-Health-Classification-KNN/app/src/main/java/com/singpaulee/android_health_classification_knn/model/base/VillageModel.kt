package com.singpaulee.android_health_classification_knn.model.base

import com.google.gson.annotations.SerializedName

data class VillageModel(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama")
    val name: String? = null

)