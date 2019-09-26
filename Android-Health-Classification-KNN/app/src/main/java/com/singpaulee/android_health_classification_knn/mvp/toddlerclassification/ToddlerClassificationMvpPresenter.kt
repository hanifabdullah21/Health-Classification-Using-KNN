package com.singpaulee.android_health_classification_knn.mvp.toddlerclassification

import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface ToddlerClassificationMvpPresenter<V : ToddlerClassificationMvpView> : MvpPresenter<V> {

    fun getListDataTraining()

    fun classificationToddler(toddler: ToddlerModel?)

    fun addClassification(toddler: ToddlerModel?)
}
