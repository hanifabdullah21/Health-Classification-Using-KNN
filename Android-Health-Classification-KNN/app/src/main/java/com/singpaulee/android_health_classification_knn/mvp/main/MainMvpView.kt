package com.singpaulee.android_health_classification_knn.mvp.main

import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface MainMvpView : MvpView {

    fun moveToLogin()
    fun moveToMainToddler()
    fun moveToMainPregnantMother()

}