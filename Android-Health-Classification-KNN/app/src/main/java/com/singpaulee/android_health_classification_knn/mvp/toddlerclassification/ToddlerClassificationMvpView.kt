package com.singpaulee.android_health_classification_knn.mvp.toddlerclassification

import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface ToddlerClassificationMvpView : MvpView {

    fun showSuccessGetDataTraining(success: Boolean)

    fun showResultClassification(toddler: ToddlerModel?)
}