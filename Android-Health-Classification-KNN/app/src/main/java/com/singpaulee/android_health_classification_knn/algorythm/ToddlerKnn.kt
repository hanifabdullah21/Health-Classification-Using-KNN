package com.singpaulee.android_health_classification_knn.algorythm

import android.util.Log
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.model.base.StatusClassificationModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlin.math.pow
import kotlin.math.sqrt

object ToddlerKnn {

    val TAG = "ProcessClassification"

    fun doClassification(
        listDataTraining: ArrayList<ToddlerModel>?,
        newData: ToddlerModel?,
        k: Int
    ): ToddlerModel? {
        val listAfterCountDistance = countDistance(listDataTraining, newData)

        val listAfterSorting = sortListByDistance(listAfterCountDistance!!)

        val result = getResult(listAfterSorting, k)

        val resultToddler = newData?.copy()
        resultToddler?.status = result

        return resultToddler
    }

    private fun countDistance(
        listDataTraining: ArrayList<ToddlerModel>?,
        newData: ToddlerModel?
    ): ArrayList<ToddlerModel>? {
        for (i in listDataTraining!!.indices) {
            val diffAge =
                (newData?.age?.toDouble()?.minus(listDataTraining[i].age?.toDouble()!!))?.pow(2)
            Log.d(TAG, "Different Age [$i] $diffAge")

            val diffWeight =
                (newData?.weight?.toDouble()?.minus(listDataTraining[i].weight?.toDouble()!!))?.pow(
                    2
                )
            Log.d(TAG, "Different Weight [$i] $diffWeight")

            val diffHeight =
                (newData?.height?.toDouble()?.minus(listDataTraining[i].height?.toDouble()!!))?.pow(
                    2
                )
            Log.d(TAG, "Different Height [$i] $diffHeight")

            val distance = sqrt(diffAge!! + diffWeight!! + diffHeight!!)
            Log.d(TAG, "Different Distance [$i] $distance")

            listDataTraining[i].distance = distance
        }
        return listDataTraining
    }

    private fun sortListByDistance(listData: ArrayList<ToddlerModel>): List<ToddlerModel> {
        return listData.sortedBy { it.distance }
    }

    private fun getResult(listAfterSorting: List<ToddlerModel>, k: Int): String {
        val buruk = StatusClassificationModel(AppContants.STATUS_MODE.STATUS_BURUK.status, 0)
        val kurang = StatusClassificationModel(AppContants.STATUS_MODE.STATUS_KURANG.status, 0)
        val baik = StatusClassificationModel(AppContants.STATUS_MODE.STATUS_BAIK.status, 0)
        val lebih = StatusClassificationModel(AppContants.STATUS_MODE.STATUS_LEBIH.status, 0)
        val obesitas = StatusClassificationModel(AppContants.STATUS_MODE.STATUS_OBESITAS.status, 0)
        val listStatus = mutableListOf<StatusClassificationModel>(buruk, kurang, baik, lebih, obesitas)
        for (i in 0 until k) {
            when {
                listAfterSorting[i].status.equals(AppContants.STATUS_MODE.STATUS_BURUK.status) -> listStatus[0].total =
                    listStatus[0].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_MODE.STATUS_KURANG.status) -> listStatus[1].total =
                    listStatus[1].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_MODE.STATUS_BAIK.status) -> listStatus[2].total =
                    listStatus[2].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_MODE.STATUS_LEBIH.status) -> listStatus[3].total =
                    listStatus[3].total!! + 1
                listAfterSorting[i].status.equals(AppContants.STATUS_MODE.STATUS_OBESITAS.status) -> listStatus[4].total =
                    listStatus[4].total!! + 1
            }
        }

        listStatus.sortByDescending { it.total }

        return listStatus[0].status.toString()
    }
}