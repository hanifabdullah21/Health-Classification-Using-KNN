package com.singpaulee.android_health_classification_knn.model.base

import com.google.gson.annotations.SerializedName
import java.util.stream.Stream

data class ToddlerModel(
    @field:SerializedName("id")
    val id : Int? = null,

    @field:SerializedName("nama")
    val name: String? = null,

    @field:SerializedName("jenis_kelamin")
    val gender: String? = null,

    @field:SerializedName("tanggal_lahir")
    val bornDate: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("dusun")
    val village: VillageModel? = null,

    @field:SerializedName("account")
    val account: AccountModel? = null
)