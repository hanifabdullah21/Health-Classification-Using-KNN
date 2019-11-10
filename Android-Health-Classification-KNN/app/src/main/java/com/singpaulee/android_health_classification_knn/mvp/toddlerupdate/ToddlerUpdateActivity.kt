package com.singpaulee.android_health_classification_knn.mvp.toddlerupdate

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.GenderModel
import com.singpaulee.android_health_classification_knn.helper.HelperGender
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.maintoddler.ToddlerMainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_toddler_update.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class ToddlerUpdateActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    var genderId: String? = null
    var villageId: Int? = null

    var toddler: ToddlerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_update)

        toddler = intent.getParcelableExtra("TODDLER")

        tra_edt_name.setText(toddler?.name.toString())
        tra_edt_born_date.setText(toddler?.bornDate.toString())

        setSpinnerGender()
        getListVillage()

        tra_btn_submit.onClick {
            updateToddler()
        }
    }

    fun setSpinnerGender() {
        val adapter = SpinnerGenderAdapter(
            this,
            R.layout.item_spinner_gender,
            HelperGender.listGender
        )
        tra_spinner_gender.adapter = adapter
        tra_spinner_gender.dropDownVerticalOffset = 120
        tra_spinner_gender.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                val genderModel: GenderModel =
                    tra_spinner_gender.getItemAtPosition(i) as GenderModel
                if (genderModel.genderSingkatanName == null) {
                    genderId = null
                } else {
                    genderId = genderModel.genderSingkatanName
                }
            }
        }

        val genderModel = when (toddler?.gender) {
            "L" -> HelperGender.listGender[1]
            "P" -> HelperGender.listGender[2]
            else -> HelperGender.listGender[0]
        }
        val genderPosition = adapter.getPosition(genderModel)
        tra_spinner_gender.setSelection(genderPosition)
        genderId = toddler?.gender
    }

    fun setSpinnerVillage(list: List<VillageModel>?) {
        val adapter = SpinnerVillageAdapter(
            this,
            R.layout.item_spinner_gender,
            list!!
        )

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
        }

        val villageSelected = toddler?.village
        val villagePosition = adapter.getPosition(villageSelected)
        tra_spinner_village.setSelection(villagePosition + 1)
        villageId = toddler?.village?.id
    }

    @SuppressLint("CheckResult")
    fun getListVillage() {
        showLoading()
        val village = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .getListVillage("Bearer " + SharedPrefManager(this).getToken())

        village.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoading()
                if (it?.statusModel?.success as Boolean) {
                    setSpinnerVillage(it.result)
                } else {

                }
            }, {
                hideLoading()
                getListVillage()
            })
    }

    @SuppressLint("CheckResult")
    fun updateToddler(){
        showLoading()
        val update = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .updateToddler("Bearer " + SharedPrefManager(this).getToken(),
                villageId,
                tra_edt_name.text.toString(),
                genderId,
                toddler?.bornDate,
                toddler?.id)

        update.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoading()
                finishAffinity()
                startActivity(intentFor<ToddlerMainActivity>())
            },{
                hideLoading()
                toast("Gagal update data balita")
            })
    }

    fun showLoading() {
        hideLoading()
        progressDialog = LoadingUtil.showLoadingDialog(this)
    }

    fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }
}
