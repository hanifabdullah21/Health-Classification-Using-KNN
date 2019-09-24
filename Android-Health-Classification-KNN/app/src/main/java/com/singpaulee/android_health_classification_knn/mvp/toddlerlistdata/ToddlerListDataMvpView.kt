package com.singpaulee.android_health_classification_knn.mvp.toddlerlistdata

import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.adapter.ToddlerMasterAdapter
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface ToddlerListDataMvpView : MvpView {

    fun showSpinnerVillage(adapter: SpinnerVillageAdapter)

    fun showRecyclerviewToddler(adapter: ToddlerMasterAdapter)

}