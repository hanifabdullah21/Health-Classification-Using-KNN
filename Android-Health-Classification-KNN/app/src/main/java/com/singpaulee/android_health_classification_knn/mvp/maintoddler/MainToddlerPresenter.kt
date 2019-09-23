package com.singpaulee.android_health_classification_knn.mvp.maintoddler

import android.content.Context
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter

class MainToddlerPresenter<V : MainToddlerMvpView>
internal constructor(val context: Context) : BasePresenter<V>(compositeDisposable = null),
    MainToddlerMvpPresenter<V> {
}