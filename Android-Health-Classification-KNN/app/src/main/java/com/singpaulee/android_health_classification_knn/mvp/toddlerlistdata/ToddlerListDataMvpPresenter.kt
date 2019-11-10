package com.singpaulee.android_health_classification_knn.mvp.toddlerlistdata

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface ToddlerListDataMvpPresenter<V : ToddlerListDataMvpView> : MvpPresenter<V> {

    fun getListVillage()

    fun getListToddlerFilter(dusunId: Int?, nama: String?)

    fun deleteToddlerData(toddlerId: Int?)
}