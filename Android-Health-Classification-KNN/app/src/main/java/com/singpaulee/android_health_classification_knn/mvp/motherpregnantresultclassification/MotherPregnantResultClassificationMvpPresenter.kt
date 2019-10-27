package com.singpaulee.android_health_classification_knn.mvp.motherpregnantresultclassification

import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface MotherPregnantResultClassificationMvpPresenter<V : MotherPregnantResultClassificationMvpView> :
    MvpPresenter<V> {

    fun getListClassification(bumilFilter: MotherPregnantModel?)

    fun getListVillage()
}