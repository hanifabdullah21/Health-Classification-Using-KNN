package com.singpaulee.android_health_classification_knn.mvp.motherpregnantresultclassification

import android.content.Context
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MotherPregnantResultClassificationPresenter<V : MotherPregnantResultClassificationMvpView>
internal constructor(compositeDisposable: CompositeDisposable, val context: Context) :
    BasePresenter<V>(compositeDisposable), MotherPregnantResultClassificationMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(context).getToken()
    }

    override fun getListClassification(bumilFilter: MotherPregnantModel?) {
        val observable = apiNetwork?.getClassificationBumil(
            "Bearer $token",
            bumilFilter?.nama,
            bumilFilter?.dusunId,
            bumilFilter?.usiaKehamilan,
            bumilFilter?.status
        )

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        if (it.result?.size == 0){
                            getMvpView()?.hideListClassification(true)
                        }else{
                            getMvpView()?.hideListClassification(false)
                            getMvpView()?.showListClassificationBumil(it.result)
                        }
                    } else {
                        getMvpView()?.showMessage(it.statusModel.message.toString())
                    }
                }, {
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

    override fun getListVillage() {
        val observable = apiNetwork?.getListVillage("Bearer $token")

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        getMvpView()?.showListVillage(it.result)
                    } else {
                        getMvpView()?.showMessage(it.statusModel.message.toString())
                    }
                }, {
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

}