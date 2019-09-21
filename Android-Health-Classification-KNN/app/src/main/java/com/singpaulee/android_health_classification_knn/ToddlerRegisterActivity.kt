package com.singpaulee.android_health_classification_knn

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.DrawableClickListener
import com.singpaulee.android_health_classification_knn.helper.GenderModel
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.HelperDate.LOCALE_ID
import com.singpaulee.android_health_classification_knn.helper.HelperDate.LOCALE_IN
import com.singpaulee.android_health_classification_knn.helper.HelperGender
import kotlinx.android.synthetic.main.activity_toddler_register.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class ToddlerRegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    DatePickerDialog.OnDateSetListener {

    var gender: String? =
        null  //Menyimpan inisial jenis kelamin "L/P" setelah user memilih jenis kelamin

    private lateinit var calendar : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_register)

        Locale.setDefault(Locale(LOCALE_IN, LOCALE_ID))
        calendar = Calendar.getInstance()

        setSpinnerAdapter()

        tra_edt_born_date.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                openDatePicker()
            }
        })
    }

    /** TODO Membuka datepicker
     *
     * */
    private fun openDatePicker() {
        DatePickerDialog(
            this,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    /** TODO Mengatur spinner adapter
     *
     * */
    private fun setSpinnerAdapter() {
        tra_spinner_gender.dropDownVerticalOffset = tra_spinner_gender.height * 2
        tra_spinner_gender.onItemSelectedListener = this
        val customSpinnerAdapter = SpinnerGenderAdapter(
            this,
            R.layout.item_spinner_gender,
            HelperGender.listGender
        )
        tra_spinner_gender.adapter = customSpinnerAdapter
        tra_spinner_gender.dropDownVerticalOffset = 120
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val sdf = SimpleDateFormat(
            HelperDate.SIMPLE_DATE_FORMAT_DMY,
            Locale(LOCALE_IN, LOCALE_ID)
        )
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        tra_edt_born_date.setText(sdf.format(calendar.time))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val genderModel: GenderModel = tra_spinner_gender.getItemAtPosition(position) as GenderModel
        if (genderModel.genderSingkatanName == null) {
            toast("Silahkan Pilih Jenis Kelamin")
            gender = null
        } else {
            toast(genderModel.genderName.toString())
            gender = genderModel.genderSingkatanName
        }
    }
}
