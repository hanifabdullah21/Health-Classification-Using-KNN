package com.singpaulee.android_health_classification_knn.mvp.toddlerregister

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.HelperGender
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.mvp.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class ToddlerRegisterPresenter<V : ToddlerRegisterMvpView>
internal constructor(compositeDisposable: CompositeDisposable?, val context: Context) :
    BasePresenter<V>(compositeDisposable), ToddlerRegisterMvpPresenter<V> {

    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    init {
        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(context).getToken()
    }

    override fun setSpinnerAdapterGender() {
        val adapter = SpinnerGenderAdapter(
            context,
            R.layout.item_spinner_gender,
            HelperGender.listGender
        )
        getMvpView()?.showSpinnerGender(adapter)
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

    override fun setDatePickerDialog() {
        Locale.setDefault(Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID))
        val calendar = Calendar.getInstance()

        val onDatePickerListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val sdf = SimpleDateFormat(
                    HelperDate.SIMPLE_DATE_FORMAT_DMY,
                    Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID)
                )
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                getMvpView()?.onShowSelectedDate(sdf.format(calendar.time))
            }

        val datePicker = DatePickerDialog(
            context,
            AlertDialog.THEME_HOLO_DARK,
            onDatePickerListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        getMvpView()?.onShowDatePicker(datePicker)
    }

    override fun validationNewDataToddler(
        name: String?,
        gender: String?,
        bornDate: String?,
        villageId: Int?
    ): Boolean {
        if (name == null || name.toString().isBlank() || name.toString().isEmpty()) {
            getMvpView()?.showValidationError(AppContants.EMPTY_NAME)
            return false
        } else if (gender == null || gender.toString().isBlank() || gender.toString().isEmpty()) {
            getMvpView()?.showValidationError(AppContants.EMPTY_GENDER)
            return false
        } else if (bornDate == null || bornDate.toString().isBlank() || bornDate.toString().isEmpty()) {
            getMvpView()?.showValidationError(AppContants.EMPTY_BORN_DATE)
            return false
        } else if (villageId == null || villageId.toString().isBlank() || villageId.toString().isEmpty()) {
            getMvpView()?.showValidationError(AppContants.EMPTY_VILLAGE)
            return false
        }
        return true
    }

    override fun submitNewDataToddler(
        name: String?,
        gender: String?,
        bornDate: String?,
        villageId: Int?
    ) {
        if (!validationNewDataToddler(name, gender, bornDate, villageId)) {
            return
        }

        val sdf = SimpleDateFormat(HelperDate.SIMPLE_DATE_FORMAT_DEFAULT, Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID))
        val sdf2 = SimpleDateFormat(HelperDate.SIMPLE_DATE_FORMAT_DMY, Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID))
        val convertDate = sdf.format(sdf2.parse(bornDate.toString())!!)

        val observable =
            apiNetwork?.addNewToddler("Bearer $token", villageId, name, gender, convertDate)

        getMvpView()?.showLoading()
        compositeDisposable?.add(
            observable!!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getMvpView()?.hideLoading()
                    if (it?.statusModel?.success as Boolean){
                        getMvpView()?.resetAllView()
                    }else{
                        getMvpView()?.showMessage(it.statusModel.message.toString())
                    }
                },{
                    getMvpView()?.hideLoading()
                    getMvpView()?.onError(it.localizedMessage!!.toString())
                })
        )
    }
}