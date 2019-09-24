package com.singpaulee.android_health_classification_knn.mvp.toddlerlistbeforeclassification

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface ToddlerListBeforeClassificationMvpPresenter<V : ToddlerListBeforeClassificationMvpView> : MvpPresenter<V> {

    fun getListVillage()

    fun getListToddlerFilter(dusunId: Int?, nama: String?)

}