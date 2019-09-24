package com.singpaulee.android_health_classification_knn.mvp.toddlerlistbeforeclassification

import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface ToddlerListBeforeClassificationMvpView : MvpView {

    fun showSpinnerVillage(adapter: SpinnerVillageAdapter)

    fun showRecyclerviewToddler(listData: ArrayList<ToddlerModel>?)

    fun emptyListData(hide: Boolean)

}