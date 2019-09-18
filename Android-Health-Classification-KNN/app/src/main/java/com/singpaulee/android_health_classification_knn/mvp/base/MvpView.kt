package com.singpaulee.android_health_classification_knn.mvp.base

import androidx.annotation.StringRes



interface MvpView {

    fun showLoading()

    fun hideLoading()

    fun onError(message: String)

    fun showMessage(message: String)

}