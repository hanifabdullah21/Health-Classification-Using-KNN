package com.singpaulee.android_health_classification_knn.model.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountModel(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("nama")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null
) : Parcelable