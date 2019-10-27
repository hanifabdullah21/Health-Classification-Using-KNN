package com.singpaulee.android_health_classification_knn.mvp.toddlerlistbeforeclassification

import android.content.Context
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ToddlerListBeforeClassificationPresenter<V : ToddlerListBeforeClassificationMvpView>
internal constructor(compositeDisposable: CompositeDisposable, val context: Context) :
    BasePresenter<V>(compositeDisposable), ToddlerListBeforeClassificationMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(context).getToken()
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
                        val adapter = SpinnerVillageAdapter(
                            context,
                            R.layout.item_spinner_gender,
                            it.result!!
                        )
                        getMvpView()?.showSpinnerVillage(adapter)
                    } else {
                        getMvpView()?.showMessage(it.statusModel.message.toString())
                    }
                }, {
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

    override fun getListToddlerFilter(dusunId: Int?, nama: String?) {
        val observable = apiNetwork?.getListBalitaFilter("Bearer $token", dusunId, nama)

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        if (it.result?.size == 0) {
                            getMvpView()?.emptyListData(true)
                        } else {
                            getMvpView()?.emptyListData(false)
                            getMvpView()?.showRecyclerviewToddler(it.result)
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

}