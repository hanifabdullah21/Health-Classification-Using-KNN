package com.singpaulee.android_health_classification_knn.mvp.login

import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter<V : LoginView>
internal constructor(compositeDisposable: CompositeDisposable) :
    BasePresenter<V>(compositeDisposable = compositeDisposable), LoginMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
    }

    override fun onButtonLoginClick(username: String?, password: String?) {
        val observable = apiNetwork?.loginAccount(username, password)

        getMvpView()?.showLoading()
        compositeDisposable.add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.status?.success as Boolean){
                        getMvpView()?.openMainActivity()
                    }else{
                        getMvpView()?.showMessage(it.status.message.toString())
                    }
                },{
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

    override fun onButtonRegisterClick() {

    }

}