package com.singpaulee.android_health_classification_knn.mvp.register

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface RegisterMvpPresenter<V : RegisterMvpView> : MvpPresenter<V>{

    fun registerNewAccount(name: String?, email: String?, username: String?, password: String?, confirmPassword: String?)

    fun validationFormRegistration(name: String?, email: String?, username: String?, password: String?, confirmPassword: String?) : Boolean

}