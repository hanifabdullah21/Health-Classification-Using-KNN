package com.singpaulee.android_health_classification_knn.mvp.main

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface MainMvpPresenter<V : MainMvpView> : MvpPresenter<V> {

    fun deleteAllSession()

}