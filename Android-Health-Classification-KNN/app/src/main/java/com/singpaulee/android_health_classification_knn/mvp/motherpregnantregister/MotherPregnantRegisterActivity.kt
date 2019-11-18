package com.singpaulee.android_health_classification_knn.mvp.motherpregnantregister

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.eventlistener.DrawableClickListener
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_mother_pregnant_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class MotherPregnantRegisterActivity : AppCompatActivity() {

    private var apiNetwork: ApiInterface? = null
    private var token: String? = null

    private var progressDialog: ProgressDialog? = null
    var villageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        apiNetwork = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
        token = SharedPrefManager(this).getToken()

        getListVillage()

        mpra_edt_born_date.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                setDatePickerDialog()
            }
        })

        mpra_edt_pregnant_date.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                setPregnantDatePickerDialog()
            }
        })

        mpra_btn_submit.onClick {
            submitNewDataBumil(
                mpra_edt_name.text.toString(),
                mpra_edt_born_date.text.toString(),
                mpra_edt_pregnant_date.text.toString(),
                villageId
            )
        }
    }

    fun submitNewDataBumil(
        name: String?,
        bornDate: String?,
        pregnantDate: String?,
        villageId: Int?
    ) {
        if (!validationNewDataBumil(name, bornDate, pregnantDate, villageId)) {
            return
        }

        val sdf = SimpleDateFormat(
            HelperDate.SIMPLE_DATE_FORMAT_DEFAULT,
            Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID)
        )
        val sdf2 = SimpleDateFormat(
            HelperDate.SIMPLE_DATE_FORMAT_DMY,
            Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID)
        )
        val convertDate = sdf.format(sdf2.parse(bornDate.toString())!!)
        val convertPregnantDate = sdf.format(sdf2.parse(pregnantDate.toString())!!)

        val observable =
            apiNetwork?.addNewBumil(
                "Bearer $token",
                villageId,
                name,
                convertDate,
                convertPregnantDate
            )

        showLoading()
        CompositeDisposable().add(
            observable!!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        resetAllView()
                    } else {
                        showMessage(it.statusModel.message.toString())
                    }
                }, {
                    hideLoading()
                    onError(it.localizedMessage!!.toString())
                })
        )
    }

    fun validationNewDataBumil(
        name: String?,
        bornDate: String?,
        pregnantDate: String?,
        villageId: Int?
    ): Boolean {
        if (name == null || name.toString().isBlank() || name.toString().isEmpty()) {
            showValidationError(AppContants.EMPTY_NAME)
            return false
        } else if (bornDate == null || bornDate.toString().isBlank() || bornDate.toString().isEmpty()) {
            showValidationError(AppContants.EMPTY_BORN_DATE)
            return false
        } else if (pregnantDate == null || pregnantDate.toString().isBlank() || pregnantDate.toString().isEmpty()) {
            showValidationError(AppContants.EMPTY_PREGNANT_DATE)
            return false
        } else if (villageId == null || villageId.toString().isBlank() || villageId.toString().isEmpty()) {
            showValidationError(AppContants.EMPTY_VILLAGE)
            return false
        }
        return true
    }

    fun showValidationError(errorCode: Int) {
        when (errorCode) {
            AppContants.EMPTY_NAME -> {
                mpra_edt_name.requestFocus()
                mpra_edt_name.error = "Tidak Boleh Kosong"
            }
            AppContants.EMPTY_GENDER -> toast("Silahkan Pilih Jenis Kelamin")
            AppContants.EMPTY_BORN_DATE -> toast("Silahkan pilih tanggal lahir")
            AppContants.EMPTY_PREGNANT_DATE -> toast("Silahkan pilih tanggal hamil")
            AppContants.EMPTY_VILLAGE -> toast("Silahkan Pilih Desa")
        }
    }

    fun resetAllView() {
        toast("Data bumil berhasil ditambahkan")
        mpra_edt_name.text = null
        mpra_edt_born_date.text = null
        mpra_edt_pregnant_date.text = null
        mpra_spinner_village.setSelection(0)
    }

    private fun getListVillage() {
        val observable = apiNetwork?.getListVillage("Bearer $token")

        showLoading()
        CompositeDisposable().add(
            observable!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoading()
                    if (it?.statusModel?.success as Boolean) {
                        val adapter = SpinnerVillageAdapter(
                            this,
                            R.layout.item_spinner_gender,
                            it.result!!
                        )
                        showSpinnerVillage(adapter)
                    } else {
                        showMessage(it.statusModel.message.toString())
                    }
                }, {
                    hideLoading()
                    onError(it.localizedMessage!!)
                })
        )
    }

    fun setDatePickerDialog() {
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
                onShowSelectedDate(sdf.format(calendar.time))
            }

        val datePicker = DatePickerDialog(
            this,
            AlertDialog.THEME_HOLO_DARK,
            onDatePickerListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        onShowDatePicker(datePicker)
    }

    fun setPregnantDatePickerDialog() {
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
                onShowSelectedPregnantDate(sdf.format(calendar.time))
            }

        val datePicker = DatePickerDialog(
            this,
            AlertDialog.THEME_HOLO_DARK,
            onDatePickerListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        onShowDatePicker(datePicker)
    }

    fun onShowDatePicker(datePickerDialog: DatePickerDialog) {
        datePickerDialog.show()
    }

    fun onShowSelectedDate(date: String) {
        mpra_edt_born_date.setText(date)
    }

    fun onShowSelectedPregnantDate(date: String) {
        mpra_edt_pregnant_date.setText(date)
    }

    fun showLoading() {
        hideLoading()
        progressDialog = LoadingUtil.showLoadingDialog(this)
    }

    fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    fun showSpinnerVillage(adapter: SpinnerVillageAdapter) {
        mpra_spinner_village.adapter = adapter
        mpra_spinner_village.dropDownVerticalOffset = 120
        mpra_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel = mpra_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
            onNothingSelected {

            }
        }
    }

    fun onError(message: String) {
        toast(message)
    }

    fun showMessage(message: String) {
        toast(message)
    }
}
