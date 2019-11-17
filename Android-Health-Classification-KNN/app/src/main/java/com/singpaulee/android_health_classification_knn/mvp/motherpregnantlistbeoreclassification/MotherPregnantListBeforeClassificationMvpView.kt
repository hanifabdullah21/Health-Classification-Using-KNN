package com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistbeoreclassification

import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface MotherPregnantListBeforeClassificationMvpView : MvpView {

    fun showSpinnerVillage(adapter: SpinnerVillageAdapter)

    fun showRecyclerviewMotherPregnant(listData: ArrayList<MotherPregnantModel>?)

    fun emptyListData(hide: Boolean)

}