package com.singpaulee.android_health_classification_knn.mvp.toddlerregister

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.DrawableClickListener
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.GenderModel
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_toddler_register.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class ToddlerRegisterActivity : AppCompatActivity(), ToddlerRegisterMvpView {

    private var progressDialog: ProgressDialog? = null

    private lateinit var presenter: ToddlerRegisterMvpPresenter<ToddlerRegisterMvpView>

    var genderId: String? = null
    var villageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = ToddlerRegisterPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListVillage()
        presenter.setSpinnerAdapterGender()

        tra_edt_born_date.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                presenter.setDatePickerDialog()
            }
        })

        tra_btn_submit.onClick {
            presenter.submitNewDataToddler(tra_edt_name.text.toString(), genderId, tra_edt_born_date.text.toString() ,villageId )
        }
    }

    override fun onShowDatePicker(datePickerDialog: DatePickerDialog) {
        datePickerDialog.show()
    }

    override fun onShowSelectedDate(date: String) {
        tra_edt_born_date.setText(date)
    }

    override fun showSpinnerGender(adapter: SpinnerGenderAdapter) {
        tra_spinner_gender.adapter = adapter
        tra_spinner_gender.dropDownVerticalOffset = 120
        tra_spinner_gender.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                val genderModel: GenderModel = tra_spinner_gender.getItemAtPosition(i) as GenderModel
                if (genderModel.genderSingkatanName == null) {
                    genderId = null
                } else {
                    genderId = genderModel.genderSingkatanName
                }
            }
        }

    }

    override fun showSpinnerVillage(adapter: SpinnerVillageAdapter) {
        tra_spinner_village.adapter = adapter
        tra_spinner_village.dropDownVerticalOffset = 120
        tra_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel = tra_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
            onNothingSelected {

            }
        }
    }

    override fun showLoading() {
        hideLoading()
        progressDialog = LoadingUtil.showLoadingDialog(this)
    }

    override fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun onError(message: String) {
        toast(message)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showValidationError(errorCode: Int) {
        when(errorCode){
            AppContants.EMPTY_NAME -> {
                tra_edt_name.requestFocus()
                tra_edt_name.error = "Tidak Boleh Kosong"
            }
            AppContants.EMPTY_GENDER -> toast("Silahkan Pilih Jenis Kelamin")
            AppContants.EMPTY_BORN_DATE -> toast("Silahkan pilih tanggal lahir")
            AppContants.EMPTY_VILLAGE -> toast("Silahkan Pilih Desa")
        }
    }

    override fun resetAllView() {
        toast("Data balita berhasil ditambahkan")
        tra_edt_name.text = null
        tra_spinner_gender.setSelection(0)
        tra_edt_born_date.text = null
        tra_spinner_village.setSelection(0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
