package com.singpaulee.android_health_classification_knn.mvp.motherpregnantaccurate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.algorythm.MotherPregnantKNN
import com.singpaulee.android_health_classification_knn.algorythm.ToddlerKnn
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_mother_pregnant_accurate.*
import kotlinx.android.synthetic.main.activity_toddler_accurate.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class MotherPregnantAccurateActivity : AppCompatActivity() {

    var token: String? = null

    var listTraining: ArrayList<MotherPregnantModel>? = null
    var listTest: ArrayList<MotherPregnantModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_accurate)

        token = SharedPrefManager(this).getToken()

        getListDataTraining()
        getListDataTest()

        mpaa_btn_submit.onClick {
            if (!validation()){
                return@onClick
            }
            countAccuration()
        }
    }

    private fun showResult(trueAccuration: Int, falseAccuration: Int) {
        mpaa_vg_result.visibility = View.VISIBLE

        mpaa_tv_true_count.text = "$trueAccuration / ${listTest?.size}"
        mpaa_tv_false_count.text = "$falseAccuration / ${listTest?.size}"

        val truePercent : Double = (trueAccuration.toDouble()/listTest?.size!!) * 100
        val falsePercent : Double= (falseAccuration.toDouble()/listTest?.size!!) * 100

        mpaa_tv_true_percent.text = String.format("%.2f", truePercent)+" %"
        mpaa_tv_false_percent.text = String.format("%.2f", falsePercent)+" %"
    }

    private fun countAccuration() {
        var trueAccuration : Int = 0
        var falseAccuration : Int = 0

        for (i in 0 until listTest?.size!!){
            val bumilTest = listTest?.get(i)
            val result = MotherPregnantKNN.doClassification(listTraining, bumilTest, mpaa_edt_inputk.text.toString().toInt())

            if (result?.status == bumilTest?.status){
                trueAccuration++
            }else{
                falseAccuration++
            }
        }

        showResult(trueAccuration, falseAccuration)
    }

    private fun validation(): Boolean {
        when{
            listTraining?.size == 0 || listTraining == null -> {
                toast("Data Training Kosong")
                return false
            }
            listTest?.size == 0 || listTest == null -> {
                toast("Data Tes Kosong")
                return false
            }
            mpaa_edt_inputk.text.toString().isNullOrBlank() -> {
                mpaa_edt_inputk.requestFocus()
                mpaa_edt_inputk.error = "Tidak boleh kosong"
                return false
            }
            else -> return true
        }
    }

    @SuppressLint("CheckResult")
    private fun getListDataTraining() {
        val training = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .getListBumilTraining("Bearer $token")

        training.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listTraining = it?.result
                showResultTraining(true)
            }, {
                listTraining = ArrayList()
                showResultTraining(true)
            })
    }

    private fun showResultTraining(success: Boolean) {
        when (success) {
            true -> {
                mpaa_tv_message_training.text =
                    "Berhasil mendapatkan data training (${listTraining?.size})"
                mpaa_iv_indicator_training.setImageResource(R.drawable.ic_checked)
            }
            false -> {
                mpaa_tv_message_training.text = "Gagal mendapatkan data training"
                mpaa_iv_indicator_training.setImageResource(R.drawable.ic_unchecked)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun getListDataTest() {
        val training = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .getListBumilTest("Bearer $token")

        training.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listTest = it?.result
                showResultTest(true)
            }, {
                listTest = ArrayList()
                showResultTest(false)
            })
    }

    private fun showResultTest(success: Boolean) {
        when (success) {
            true -> {
                mpaa_tv_message_test.text = "Berhasil mendapatkan data test (${listTest?.size})"
                mpaa_iv_indicator_test.setImageResource(R.drawable.ic_checked)
            }
            false -> {
                mpaa_tv_message_test.text = "Gagal mendapatkan data test"
                mpaa_iv_indicator_test.setImageResource(R.drawable.ic_unchecked)
            }
        }
    }
}
