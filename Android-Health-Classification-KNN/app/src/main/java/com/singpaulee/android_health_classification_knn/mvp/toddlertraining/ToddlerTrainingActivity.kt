package com.singpaulee.android_health_classification_knn.mvp.toddlertraining

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerStatusToddlerAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.GenderModel
import com.singpaulee.android_health_classification_knn.helper.HelperGender
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_toddler_training.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class ToddlerTrainingActivity : AppCompatActivity() {

    var genderId: String? = null
    var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_training)

        val data = intent.getStringExtra("BALITA")

        setSpinnerGender()
        setSpinnerStatus()

        tta_btn_submit.onClick {
            if (!validation()) {
                return@onClick
            }
            if (data == "TRAINING"){
                sendDataTraining()
            }else if (data == "TEST"){
                sendDataTest()
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun sendDataTraining() {
        val token = SharedPrefManager(this).getToken()
        val post = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .addBalitaTraining(
                "Bearer "+token,
                tta_edt_age.text.toString().toInt(),
                tta_edt_height.text.toString().toDouble(),
                tta_edt_weight.text.toString().toDouble(),
                genderId,
                status
            )

        post.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast("Data Training Balita berhasil ditambahkan")
                tta_edt_name.text = null
                tta_edt_age.text = null
                tta_edt_height.text = null
                tta_edt_weight.text = null
                tta_spinner_gender.setSelection(0)
                genderId = null
                tta_spinner_status.setSelection(0)
                status = null
            },{
                toast("Data Training Balita Gagal Ditambahkan")
                Log.d("POSTBALITATRAINING", it.localizedMessage)
            })

    }

    @SuppressLint("CheckResult")
    private fun sendDataTest() {
        val token = SharedPrefManager(this).getToken()
        val post = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .addBalitaTest(
                "Bearer "+token,
                tta_edt_age.text.toString().toInt(),
                tta_edt_height.text.toString().toDouble(),
                tta_edt_weight.text.toString().toDouble(),
                genderId,
                status
            )

        post.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast("Data Training Balita berhasil ditambahkan")
                tta_edt_name.text = null
                tta_edt_age.text = null
                tta_edt_height.text = null
                tta_edt_weight.text = null
                tta_spinner_gender.setSelection(0)
                genderId = null
                tta_spinner_status.setSelection(0)
                status = null
            },{
                toast("Data Training Balita Gagal Ditambahkan")
                Log.d("POSTBALITATRAINING", it.localizedMessage)
            })

    }

    private fun validation(): Boolean {
        when {
            tta_edt_name.text.toString().isNullOrBlank() || tta_edt_name.text.toString().isNullOrEmpty() -> {
                tta_edt_name.error = "Wajib diisi"
                tta_edt_name.requestFocus()
                return false
            }
            genderId == null || genderId == "" -> {
                toast("Silahkan pilih jenis kelamin")
                return false
            }
            tta_edt_age.text.toString().isNullOrBlank() || tta_edt_age.text.toString().isNullOrEmpty() -> {
                tta_edt_age.error = "Wajib diisi"
                tta_edt_age.requestFocus()
                return false
            }
            tta_edt_weight.text.toString().isNullOrBlank() || tta_edt_weight.text.toString().isNullOrEmpty() -> {
                tta_edt_weight.error = "Wajib diisi"
                tta_edt_weight.requestFocus()
                return false
            }
            tta_edt_height.text.toString().isNullOrBlank() || tta_edt_height.text.toString().isNullOrEmpty() -> {
                tta_edt_height.error = "Wajib diisi"
                tta_edt_height.requestFocus()
                return false
            }
            status == null || status == "" -> {
                toast("Silahkan pilih status")
                return false
            }
            else -> return true
        }
    }

    private fun setSpinnerStatus() {
        val statusList = listOf<String>(
            "Silahkan pilih status",
            AppContants.STATUS_MODE.STATUS_BURUK.status,
            AppContants.STATUS_MODE.STATUS_KURANG.status,
            AppContants.STATUS_MODE.STATUS_BAIK.status,
            AppContants.STATUS_MODE.STATUS_LEBIH.status,
            AppContants.STATUS_MODE.STATUS_OBESITAS.status
        )

        val adapter = SpinnerStatusToddlerAdapter(
            this,
            R.layout.item_spinner_gender,
            statusList
        )

        tta_spinner_status.adapter = adapter
        tta_spinner_status.dropDownVerticalOffset = 120
        tta_spinner_status.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    status = null
                } else {
                    status = tta_spinner_status.getItemAtPosition(i).toString()
                }
            }
            onNothingSelected {

            }
        }
    }

    private fun setSpinnerGender() {
        val adapter = SpinnerGenderAdapter(
            this,
            R.layout.item_spinner_gender,
            HelperGender.listGender
        )
        tta_spinner_gender.adapter = adapter
        tta_spinner_gender.dropDownVerticalOffset = 120
        tta_spinner_gender.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                val genderModel: GenderModel =
                    tta_spinner_gender.getItemAtPosition(i) as GenderModel
                if (genderModel.genderSingkatanName == null) {
                    genderId = null
                } else {
                    genderId = genderModel.genderSingkatanName
                }
            }
        }
    }


}
