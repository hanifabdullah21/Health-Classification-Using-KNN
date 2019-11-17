package com.singpaulee.android_health_classification_knn.model.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MotherPregnantModel(

    @field:SerializedName("tanggal_posyandu")
    var posyanduDate: String? = null,

    @field:SerializedName("dusun")
    var dusun: VillageModel? = null,

    @field:SerializedName("berat_badan")
    val beratBadan: Double? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("usia_bumil")
    val usiaBumil: Int? = null,

    @field:SerializedName("usia_kehamilan")
    var usiaKehamilan: Int? = null,

    @field:SerializedName("dusun_id")
    var dusunId: Int? = null,

    @field:SerializedName("KEK")
    var kEK: Int? = null,

    @field:SerializedName("LILA")
    val lILA: Double? = null,

    @field:SerializedName("account_id")
    val accountId: Int? = null,

    @field:SerializedName("nama")
    var nama: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("tinggi_badan")
    val tinggiBadan: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("umur")
    val umur: Int? = null,

    @field:SerializedName("distance")
    var distance: Double? = null
) : Parcelable