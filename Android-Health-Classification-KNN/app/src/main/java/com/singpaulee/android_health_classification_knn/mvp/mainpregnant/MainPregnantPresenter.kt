package com.singpaulee.android_health_classification_knn.mvp.mainpregnant

import android.content.Context
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter

class MainPregnantPresenter <V : MainPregnantMvpView>
internal constructor(val context: Context) : BasePresenter<V>(compositeDisposable = null), MainPregnantMvpPresenter<V> {


}