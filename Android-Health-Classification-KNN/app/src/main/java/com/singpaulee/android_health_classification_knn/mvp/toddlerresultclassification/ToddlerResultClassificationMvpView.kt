package com.singpaulee.android_health_classification_knn.mvp.toddlerresultclassification

import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface ToddlerResultClassificationMvpView : MvpView {

    fun openDialogFilter()

    fun showListClassificationToddler(listClassificationToddler: ArrayList<ToddlerModel>?)

    fun showListVillage(listVillage : List<VillageModel>?)
}