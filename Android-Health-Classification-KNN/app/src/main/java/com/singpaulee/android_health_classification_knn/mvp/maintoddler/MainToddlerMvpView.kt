package com.singpaulee.android_health_classification_knn.mvp.maintoddler

import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface MainToddlerMvpView : MvpView {

    fun moveToToddlerRegister()
    fun moveToToddlerData()
    fun moveToToddlerClassification()
    fun moveToToddlerResult()

}