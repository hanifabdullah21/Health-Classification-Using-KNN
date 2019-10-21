package com.singpaulee.android_health_classification_knn.mvp.motherpregnantresultclassification

import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface MotherPregnantResultClassificationMvpView : MvpView {

    fun openDialogFilter()

    fun showListClassificationBumil(listClassification: ArrayList<MotherPregnantModel>?)

    fun showListVillage(listVillage: List<VillageModel>?)

    fun hideListClassification(hide: Boolean)
}