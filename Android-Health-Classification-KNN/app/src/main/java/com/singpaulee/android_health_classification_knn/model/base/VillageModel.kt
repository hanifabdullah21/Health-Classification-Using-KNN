package com.singpaulee.android_health_classification_knn.model.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VillageModel(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama")
    val name: String? = null

) : Parcelable