package com.singpaulee.android_health_classification_knn.mvp.main

import android.content.Context
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter

class MainPresenter<V : MainMvpView>
internal constructor(val context: Context) : BasePresenter<V>(compositeDisposable = null),
    MainMvpPresenter<V> {

    override fun deleteAllSession() {
        val sharedPrefManager = SharedPrefManager(context)
        sharedPrefManager.setToken(null)
        sharedPrefManager.setIsLoggedIn(false)
        getMvpView()?.moveToLogin()
    }

}