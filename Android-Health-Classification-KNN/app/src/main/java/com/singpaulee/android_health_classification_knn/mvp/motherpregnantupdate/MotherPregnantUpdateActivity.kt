package com.singpaulee.android_health_classification_knn.mvp.motherpregnantupdate

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.eventlistener.DrawableClickListener
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.mainpregnant.MainPregnantActivity
import com.singpaulee.android_health_classification_knn.mvp.maintoddler.ToddlerMainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_mother_pregnant_register.*
import kotlinx.android.synthetic.main.activity_mother_pregnant_update.*
import kotlinx.android.synthetic.main.activity_toddler_update.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class MotherPregnantUpdateActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    var villageId: Int? = null

    var mp: MotherPregnantModel? = null

    val sdf = SimpleDateFormat(
        HelperDate.SIMPLE_DATE_FORMAT_DEFAULT,
        Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID)
    )
    val sdf2 = SimpleDateFormat(
        HelperDate.SIMPLE_DATE_FORMAT_DMY,
        Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_update)

        mp = intent.getParcelableExtra("MP")

        mpua_edt_name.setText(mp?.nama.toString())
        mpua_edt_born_date.setText(sdf2.format(sdf.parse(mp?.bornDate.toString())))
        mpua_edt_pregnant_date.setText(sdf2.format(sdf.parse(mp?.pregnantDate.toString())))
//        mpua_edt_pregnant_date.setText(mp?.pregnantDate.toString())

        getListVillage()

        mpua_edt_pregnant_date.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                setPregnantDatePickerDialog()
            }
        })

        mpua_btn_submit.onClick {
            updateMotherPregnant()
        }
    }

    @SuppressLint("CheckResult")
    fun updateMotherPregnant(){
        val convertPregnantDate = sdf.format(sdf2.parse(mpua_edt_pregnant_date.text.toString())!!)

        showLoading()
        val update = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .updateBumil("Bearer " + SharedPrefManager(this).getToken(),
                villageId,
                mpua_edt_name.text.toString(),
                mp?.bornDate,
                convertPregnantDate,
                mp?.id)

        update.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoading()
                finishAffinity()
                startActivity(intentFor<MainPregnantActivity>())
            },{
                hideLoading()
                toast("Gagal update data balita")
            })
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

    fun onShowDatePicker(datePickerDialog: DatePickerDialog) {
        datePickerDialog.show()
    }

    fun onShowSelectedDate(date: String) {
        mpua_edt_pregnant_date.setText(date)
    }

    fun setSpinnerVillage(list: List<VillageModel>?) {
        val adapter = SpinnerVillageAdapter(
            this,
            R.layout.item_spinner_gender,
            list!!
        )

        mpua_spinner_village.adapter = adapter
        mpua_spinner_village.dropDownVerticalOffset = 120
        mpua_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel = mpua_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
        }

        val villageSelected = mp?.dusun
        val villagePosition = adapter.getPosition(villageSelected)
        mpua_spinner_village.setSelection(villagePosition + 1)
        villageId = mp?.dusun?.id
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

    fun showLoading() {
        hideLoading()
        progressDialog = LoadingUtil.showLoadingDialog(this)
    }

    fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }
}
