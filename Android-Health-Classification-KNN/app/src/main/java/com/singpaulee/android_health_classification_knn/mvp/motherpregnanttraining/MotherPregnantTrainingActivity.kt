package com.singpaulee.android_health_classification_knn.mvp.motherpregnanttraining

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerStatusToddlerAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_mother_pregnant_training.*
import kotlinx.android.synthetic.main.activity_toddler_training.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class MotherPregnantTrainingActivity : AppCompatActivity() {

    var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_training)

        setSpinnserStatus()

        mpta_btn_submit.onClick {
            if(!validation()){
                return@onClick
            }
            addBumilTraining()
        }
    }

    @SuppressLint("CheckResult")
    private fun addBumilTraining() {
        val token = SharedPrefManager(this).getToken()
        val post = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .addBumilTraining(
                "Bearer $token",
                mpta_edt_name.text.toString(),
                mpta_edt_age.text.toString().toInt(),
                mpta_edt_age_pregnant.text.toString().toInt(),
                mpta_edt_weight.text.toString().toDouble(),
                mpta_edt_height.text.toString().toDouble(),
                status
            )

        post.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast("Berhasil menambahkan data training bumil")

                mpta_edt_name.text = null
                mpta_edt_age.text = null
                mpta_edt_age_pregnant.text = null
                mpta_edt_weight.text = null
                mpta_edt_height.text = null
                mpta_spinner_status.setSelection(0)
                status = null
            },{
                toast("Gagal menambahkan data training bumil")
            })
    }

    private fun validation(): Boolean {
        when{
            mpta_edt_name.text.toString().isNullOrBlank() || mpta_edt_name.text.toString().isNullOrEmpty() -> {
                mpta_edt_name.error = "Tidak boleh kosong"
                mpta_edt_name.requestFocus()
                return false
            }
            mpta_edt_age.text.toString().isNullOrBlank() || mpta_edt_age.text.toString().isNullOrEmpty() -> {
                mpta_edt_age.error = "Tidak boleh kosong"
                mpta_edt_age.requestFocus()
                return false
            }
            mpta_edt_age_pregnant.text.toString().isNullOrBlank() || mpta_edt_age_pregnant.text.toString().isNullOrEmpty() -> {
                mpta_edt_age_pregnant.error = "Tidak boleh kosong"
                mpta_edt_age_pregnant.requestFocus()
                return false
            }
            mpta_edt_weight.text.toString().isNullOrBlank() || mpta_edt_weight.text.toString().isNullOrEmpty() -> {
                mpta_edt_weight.error = "Tidak boleh kosong"
                mpta_edt_weight.requestFocus()
                return false
            }
            mpta_edt_height.text.toString().isNullOrBlank() || mpta_edt_height.text.toString().isNullOrEmpty() -> {
                mpta_edt_name.error = "Tidak boleh kosong"
                mpta_edt_name.requestFocus()
                return false
            }
            status==null || status=="" -> {
                toast("Harap memilih status")
                return false
            }
            else -> return true
        }

    }

    private fun setSpinnserStatus() {
        val statusList = listOf<String>(
            "Silahkan pilih status",
            AppContants.STATUS_PREGNANT.STATUS_KURANG.status,
            AppContants.STATUS_PREGNANT.STATUS_NORMAL.status,
            AppContants.STATUS_PREGNANT.STATUS_OVERWEIGHT.status,
            AppContants.STATUS_PREGNANT.STATUS_OBESITAS.status
        )

        val adapter = SpinnerStatusToddlerAdapter(
            this,
            R.layout.item_spinner_gender,
            statusList
        )

        mpta_spinner_status.adapter = adapter
        mpta_spinner_status.dropDownVerticalOffset = 120
        mpta_spinner_status.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    status = null
                } else {
                    status = mpta_spinner_status.getItemAtPosition(i).toString()
                }
            }
            onNothingSelected {

            }
        }
    }
}
