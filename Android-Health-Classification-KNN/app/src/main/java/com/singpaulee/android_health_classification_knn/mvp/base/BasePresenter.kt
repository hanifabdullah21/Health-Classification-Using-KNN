package com.singpaulee.android_health_classification_knn.mvp.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView>
internal constructor(protected val compositeDisposable: CompositeDisposable?) : MvpPresenter<V> {

    private var mMvpView: V? = null

    override fun onAttach(mvpView: V) {
        mMvpView = mvpView
    }

    override fun onDetach() {
        compositeDisposable?.dispose()
        mMvpView = null
    }

    fun getMvpView(): V? {
        return mMvpView
    }


}