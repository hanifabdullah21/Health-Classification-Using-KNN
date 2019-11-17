package com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistbeoreclassification

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface MotherPregnantListBeforeClassificationMvpPresenter<V : MotherPregnantListBeforeClassificationMvpView> : MvpPresenter<V> {

    fun getListVillage()

    fun getListMotherPregnantFilter(dusunId: Int?, nama: String?)

}