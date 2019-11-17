package com.singpaulee.android_health_classification_knn.algorythm

import android.util.Log
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.StatusClassificationModel
import kotlin.math.pow
import kotlin.math.sqrt

object MotherPregnantKNN {

    val TAG = "ProcessClassification"

    fun doClassification(
        listDataTraining: ArrayList<MotherPregnantModel>?,
        newData: MotherPregnantModel?,
        k: Int
    ): MotherPregnantModel? {
        val listAfterCountDistance = countDistance(listDataTraining, newData)

        val listAfterSorting = sortListByDistance(listAfterCountDistance!!)

        val result = getResult(listAfterSorting, k)

        val resultToddler = newData?.copy()
        resultToddler?.status = result

        return resultToddler
    }

    private fun getResult(listAfterSorting: List<MotherPregnantModel>, k: Int): String? {
        val kurang = StatusClassificationModel(AppContants.STATUS_PREGNANT.STATUS_KURANG.status, 0)
        val normal = StatusClassificationModel(AppContants.STATUS_PREGNANT.STATUS_NORMAL.status, 0)
        val overweight =
            StatusClassificationModel(AppContants.STATUS_PREGNANT.STATUS_OVERWEIGHT.status, 0)
        val obesitas =
            StatusClassificationModel(AppContants.STATUS_PREGNANT.STATUS_OBESITAS.status, 0)
        val listStatus =
            mutableListOf<StatusClassificationModel>(kurang, normal, overweight, obesitas)
        for (i in 0 until k) {
            Log.d(TAG,"STATUS GIZI "+listAfterSorting[i].status.toString())
            when {
                listAfterSorting[i].status.equals(AppContants.STATUS_PREGNANT.STATUS_KURANG.status) -> listStatus[0].total =
                    listStatus[0].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_PREGNANT.STATUS_NORMAL.status) -> listStatus[1].total =
                    listStatus[1].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_PREGNANT.STATUS_OVERWEIGHT.status) -> listStatus[2].total =
                    listStatus[2].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_PREGNANT.STATUS_OBESITAS.status) -> listStatus[3].total =
                    listStatus[3].total!! + 1
            }
        }

        listStatus.sortByDescending { it.total }

        return listStatus[0].status.toString()
    }

    private fun sortListByDistance(listAfterCountDistance: ArrayList<MotherPregnantModel>): List<MotherPregnantModel> {
        return listAfterCountDistance.sortedBy { it.distance }
    }

    private fun countDistance(
        listDataTraining: ArrayList<MotherPregnantModel>?,
        newData: MotherPregnantModel?
    ): ArrayList<MotherPregnantModel>? {
        for (i in listDataTraining!!.indices) {
            val diffAge =
                (newData?.usiaBumil?.toDouble()?.minus(listDataTraining[i].usiaBumil?.toDouble()!!))?.pow(
                    2
                )
            Log.d(TAG, "Different Age [$i] $diffAge")

            val diffPregnantAge =
                (newData?.usiaKehamilan?.toDouble()?.minus(listDataTraining[i].usiaKehamilan?.toDouble()!!))?.pow(
                    2
                )
            Log.d(TAG, "Different Pregnant Age [$i] $diffPregnantAge")

            val diffWeight =
                (newData?.beratBadan?.toDouble()?.minus(listDataTraining[i].beratBadan?.toDouble()!!))?.pow(
                    2
                )
            Log.d(ToddlerKnn.TAG, "Different Weight [$i] $diffWeight")

            val diffHeight =
                (newData?.tinggiBadan?.toDouble()?.minus(listDataTraining[i].tinggiBadan?.toDouble()!!))?.pow(
                    2
                )
            Log.d(ToddlerKnn.TAG, "Different Height [$i] $diffHeight")

//            val diffLILA =
//                (newData?.lILA?.toDouble()?.minus(listDataTraining[i].lILA?.toDouble()!!))?.pow(
//                    2
//                )
//            Log.d(TAG, "Different Age [$i] $diffLILA")

            val distance =
//                sqrt(diffAge!! + diffPregnantAge!! + diffWeight!! + diffHeight!! + diffLILA!!)
                sqrt(diffAge!! + diffPregnantAge!! + diffWeight!! + diffHeight!!)
            Log.d(ToddlerKnn.TAG, "Different Distance [$i] $distance")

            listDataTraining[i].distance = distance
        }
        return listDataTraining

    }
}