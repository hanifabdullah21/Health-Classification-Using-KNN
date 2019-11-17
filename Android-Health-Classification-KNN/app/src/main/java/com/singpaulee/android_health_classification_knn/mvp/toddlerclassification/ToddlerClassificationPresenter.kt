package com.singpaulee.android_health_classification_knn.mvp.toddlerclassification

import android.content.Context
import com.singpaulee.android_health_classification_knn.algorythm.ToddlerKnn
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ToddlerClassificationPresenter<V : ToddlerClassificationMvpView>
internal constructor(compositeDisposable: CompositeDisposable, context: Context) :
    BasePresenter<V>(compositeDisposable), ToddlerClassificationMvpPresenter<V> {


    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    var listTrainingToddler: ArrayList<ToddlerModel>? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(context).getToken()
    }

    override fun getListDataTraining(gender: String?) {
        val observable = apiNetwork?.getListBalitaTraining("Bearer $token", gender)

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        getMvpView()?.showSuccessGetDataTraining(true)
                        listTrainingToddler = it.result
                    } else {
                        getMvpView()?.showMessage(it.statusModel.message.toString())
                        getMvpView()?.showSuccessGetDataTraining(false)
                    }
                }, {
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!)
                })
        )
    }

    override fun classificationToddler(toddler: ToddlerModel?, k: Int?) {
        val resultToddler = ToddlerKnn.doClassification(listTrainingToddler, toddler, k!!)
        addClassification(resultToddler)
    }

    override fun validationInput(height: String?, weight: String?): Boolean {
        if (height.toString().isEmpty() || height.toString().isBlank()) {
            return false
        } else if (weight.toString().isEmpty() || weight.toString().isBlank()) {
            return false
        }
        return true
    }

    override fun addClassification(toddler: ToddlerModel?) {
        val observable = apiNetwork?.postClassification(
            "Bearer $token",
            toddler?.id,
            toddler?.age,
            toddler?.posyanduDate,
            toddler?.height,
            toddler?.weight,
            toddler?.status
        )

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        getMvpView()?.showResultClassification(it.result)
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