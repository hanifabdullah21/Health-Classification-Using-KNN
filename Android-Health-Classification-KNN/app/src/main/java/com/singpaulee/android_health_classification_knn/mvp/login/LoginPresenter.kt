package com.singpaulee.android_health_classification_knn.mvp.login

import android.content.Context
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter<V : LoginView>
internal constructor(compositeDisposable: CompositeDisposable, val context: Context) :
    BasePresenter<V>(compositeDisposable = compositeDisposable), LoginMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
    }

    override fun checkIsLoggedIn() {
        if (SharedPrefManager(context).getIsLoggedIn()){
            getMvpView()?.openMainActivity()
        }
    }

    override fun onButtonLoginClick(username: String?, password: String?) {
        if (!validation(username, password)) {
            return
        }
        val observable = apiNetwork?.loginAccount(username, password)

        getMvpView()?.showLoading()
        compositeDisposable.add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.status?.success as Boolean) {
                        val sharedPrefManager = SharedPrefManager(context)
                        sharedPrefManager.setIsLoggedIn(true)
                        sharedPrefManager.setToken(it.auth?.token.toString())
                        getMvpView()?.openMainActivity()
                    } else {
                        getMvpView()?.showMessage(it.status.message.toString())
                    }
                }, {
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

    override fun validation(username: String?, password: String?): Boolean {
        if (username.toString().isEmpty() || username.toString().isBlank()) {
            getMvpView()?.showValidationError(AppContants.EMPTY_USERNAME)
            return false
        } else if (password.toString().isEmpty() || password.toString().isBlank()) {
            getMvpView()?.showValidationError(AppContants.EMPTY_PASSWORD)
            return false
        }
        return true
    }

    override fun onButtonRegisterClick() {

    }

}