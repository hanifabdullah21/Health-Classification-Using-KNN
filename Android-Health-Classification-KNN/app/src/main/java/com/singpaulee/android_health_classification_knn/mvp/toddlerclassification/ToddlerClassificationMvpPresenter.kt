package com.singpaulee.android_health_classification_knn.mvp.toddlerclassification

import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface ToddlerClassificationMvpPresenter<V : ToddlerClassificationMvpView> : MvpPresenter<V> {

    fun getListDataTraining(gender: String?)

    fun classificationToddler(toddler: ToddlerModel?)

    fun addClassification(toddler: ToddlerModel?)

    fun validationInput(height: String?, weight: String?) : Boolean
}
