package com.singpaulee.android_health_classification_knn.mvp.motherpregnantclassification

import android.content.Context
import android.util.Log
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.algorythm.MotherPregnantKNN
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MotherPregnantClassificationPresenter<V : MotherPregnantClassificationMvpView>
internal constructor(val context: Context, compositeDisposable: CompositeDisposable) :
    BasePresenter<V>(compositeDisposable), MotherPregnantClassificationMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    var listTrainingBumil: ArrayList<MotherPregnantModel>? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(context).getToken()
    }

    override fun getListDataTraining() {
        val observable = apiNetwork?.getListBumilTraining("Bearer $token")

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        getMvpView()?.showSuccessGetDataTraining(true)
                        listTrainingBumil = it.result
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

    override fun classificationMotherPregnant(motherPregnant: MotherPregnantModel?) {
        val result = MotherPregnantKNN.doClassification(listTrainingBumil, motherPregnant, 3)
        val kek : Int = if(motherPregnant?.lILA!! < 23.5)  1 else 0
        result?.kEK = kek

        Log.d("KNN", result.toString())
        addClassification(result)
    }

    override fun addClassification(motherPregnant: MotherPregnantModel?) {
        val observable = apiNetwork?.addBumilClassification(
            "Bearer $token",
            motherPregnant?.dusunId,
            motherPregnant?.nama,
            motherPregnant?.usiaBumil,
            motherPregnant?.usiaKehamilan,
            motherPregnant?.beratBadan,
            motherPregnant?.tinggiBadan,
            motherPregnant?.lILA,
            motherPregnant?.kEK!!,
            motherPregnant.status,
            motherPregnant.posyanduDate
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

    override fun validationInput(
        name: String?,
        age: String?,
        pregnantAge: String?,
        villageId: Int?,
        height: String?,
        weight: String?,
        LILA: String?
    ): Boolean {
        when {
            name.isNullOrBlank() || name.isNullOrEmpty() -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_NAME)
                return false
            }
            age.isNullOrBlank() || age.isNullOrEmpty() -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_AGE)
                return false
            }
            pregnantAge.isNullOrBlank() || pregnantAge.isNullOrEmpty() -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_PREGNANT_AGE)
                return false
            }
            villageId==null -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_VILLAGE)
                return false
            }
            weight.isNullOrBlank() || weight.isNullOrEmpty() -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_WEIGHT)
                return false
            }
            height.isNullOrBlank() || height.isNullOrEmpty() -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_HEIGHT)
                return false
            }
            LILA.isNullOrBlank() || LILA.isNullOrEmpty() -> {
                getMvpView()?.showValidationError(AppContants.EMPTY_LILA)
                return false
            }
            else -> return true
        }
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
}