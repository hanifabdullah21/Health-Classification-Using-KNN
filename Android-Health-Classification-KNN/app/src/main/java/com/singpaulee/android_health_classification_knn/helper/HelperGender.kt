package com.singpaulee.android_health_classification_knn.helper

import com.singpaulee.android_health_classification_knn.R

object HelperGender {

    private val genderNull = GenderModel("Silahkan pilih jenis kelamin", null, R.drawable.ic_gender)
    private val genderMan = GenderModel("Laki-laki", "L", R.drawable.ic_gender_man)
    private val genderWomen = GenderModel("Perempuan", "P", R.drawable.ic_gender_women)

    val listGender: List<GenderModel> = mutableListOf(genderNull,genderMan, genderWomen)

}

data class GenderModel(
    val genderName: String? = null,
    val genderSingkatanName: String? = null,
    val genderImage: Int? = null
){
    override fun toString(): String {
        return "$genderName"
    }
}