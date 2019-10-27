package com.singpaulee.android_health_classification_knn.mvp.register

import android.content.Context
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterPresenter<V : RegisterMvpView>
internal constructor(compositeDisposable: CompositeDisposable, context: Context) :
    BasePresenter<V>(compositeDisposable), RegisterMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(context).getToken()
    }

    override fun registerNewAccount(
        name: String?,
        email: String?,
        username: String?,
        password: String?,
        confirmPassword: String?
    ) {
        if (!validationFormRegistration(name, email, username, password, confirmPassword)){
            return
        }

        val observable = apiNetwork?.registerAccount(email, username, name, password)

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.status?.success as Boolean){
                        getMvpView()?.resetAllView()
                    }else{
                        getMvpView()?.showMessage(it.status.message.toString())
                    }
                },{
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

    override fun validationFormRegistration(
        name: String?,
        email: String?,
        username: String?,
        password: String?,
        confirmPassword: String?
    ): Boolean {
        if (name == null || name.toString().isBlank() || name.toString().isEmpty()){
            getMvpView()?.showValidationError(AppContants.EMPTY_NAME)
            return false
        }else if (email == null || email.toString().isBlank() || email.toString().isEmpty()){
            getMvpView()?.showValidationError(AppContants.EMPTY_EMAIL)
            return false
        }else if (!email.contains('@')){
            getMvpView()?.showValidationError(AppContants.FALSE_EMAIL_FORMAT)
            return false
        }else if (username == null || username.toString().isBlank() || username.toString().isEmpty()){
            getMvpView()?.showValidationError(AppContants.EMPTY_USERNAME)
            return false
        }else if (username.contains(' ')){
            getMvpView()?.showValidationError(AppContants.FALSE_USERNAME_FORMAT)
            return false
        }else if (password == null || password.toString().isBlank() || password.toString().isEmpty()){
            getMvpView()?.showValidationError(AppContants.EMPTY_PASSWORD)
            return false
        }else if (confirmPassword == null || confirmPassword.toString().isBlank() || confirmPassword.toString().isEmpty()){
            getMvpView()?.showValidationError(AppContants.EMPTY_CONFIRM_PASSWORD)
            return false
        }else if (confirmPassword != password){
            getMvpView()?.showValidationError(AppContants.FALSE_CONFIRM_PASSWORD)
            return false
        }
        return true
    }


}