package com.singpaulee.android_health_classification_knn.model.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToddlerModel(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama")
    val name: String? = null,

    @field:SerializedName("umur")
    val age: String? = null,

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
    val account: AccountModel? = null,

    @field:SerializedName("tinggi_badan")
    var height: Int? = null,

    @field:SerializedName("berat_badan")
    var weight: Int? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("distance")
    var distance: Double? = null
) : Parcelable