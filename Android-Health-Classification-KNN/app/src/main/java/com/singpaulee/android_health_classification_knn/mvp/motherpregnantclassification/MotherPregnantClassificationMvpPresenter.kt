package com.singpaulee.android_health_classification_knn.mvp.motherpregnantclassification

import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface MotherPregnantClassificationMvpPresenter<V : MotherPregnantClassificationMvpView> :
    MvpPresenter<V> {

    fun getListDataTraining()

    fun classificationMotherPregnant(motherPregnant: MotherPregnantModel?)

    fun addClassification(motherPregnant: MotherPregnantModel?)

    fun validationInput(
        name: String?,
        age: String?,
        pregnantAge: String?,
        villageId: Int?,
        height: String?,
        weight: String?,
        LILA: String?
    ): Boolean
}