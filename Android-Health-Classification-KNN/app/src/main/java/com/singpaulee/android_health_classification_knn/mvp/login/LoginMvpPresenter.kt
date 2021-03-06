package com.singpaulee.android_health_classification_knn.mvp.login

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface LoginMvpPresenter<V : LoginView> : MvpPresenter<V> {

    fun validation(username: String?, password: String?) : Boolean
    fun checkIsLoggedIn()
    fun onButtonLoginClick(username: String?, password: String?)
    fun onButtonRegisterClick()

}