package com.singpaulee.android_health_classification_knn.mvp.toddlerresultclassification

import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface ToddlerResultClassificationMvpPresenter<V : ToddlerResultClassificationMvpView> : MvpPresenter<V> {

    fun getListClassification(toddlerFilter: ToddlerModel?)

    fun getListVillage()
}