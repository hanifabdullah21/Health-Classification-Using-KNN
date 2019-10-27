package com.singpaulee.android_health_classification_knn.mvp.base


interface MvpPresenter<V : MvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()

}