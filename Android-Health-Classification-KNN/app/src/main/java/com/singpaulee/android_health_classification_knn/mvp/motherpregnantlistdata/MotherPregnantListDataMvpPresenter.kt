package com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistdata

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface MotherPregnantListDataMvpPresenter<V : MotherPregnantListDataMvpView> : MvpPresenter<V> {

    fun getListVillage()

    fun getListMotherPregnantFilter(dusunId: Int?, nama: String?)

    fun deleteMotherPregnantData(toddlerId: Int?)
}