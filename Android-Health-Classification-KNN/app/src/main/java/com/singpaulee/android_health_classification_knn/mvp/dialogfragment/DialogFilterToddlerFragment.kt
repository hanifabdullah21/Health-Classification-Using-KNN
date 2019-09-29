package com.singpaulee.android_health_classification_knn.mvp.dialogfragment


import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.DrawableClickListener
import com.singpaulee.android_health_classification_knn.helper.GenderModel
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.HelperGender
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.*
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.*
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DialogFilterToddlerFragment : DialogFragment(), View.OnClickListener {
    lateinit var rootView: View

    val calendar = Calendar.getInstance()

    var genderId: String? = null
    var villageId: Int? = null
    var dateFrom: String? = null
    var dateUntil: String? = null

    val onDateFromListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        dateFrom = HelperDate.dateFormatDefault(calendar.time)
        val dateShow = HelperDate.dateFormatDmy(calendar.time)
        rootView.fdft_edt_posyandu_date_from.setText(dateShow)
    }

    val onDateUntilListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        dateUntil = HelperDate.dateFormatDefault(calendar.time)
        val dateShow = HelperDate.dateFormatDmy(calendar.time)
        rootView.fdft_edt_posyandu_date_until.setText(dateShow)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_dialog_filter_toddler, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.semiTransparent)))
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
        Locale.setDefault(Locale(HelperDate.LOCALE_IN, HelperDate.LOCALE_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSpinnerGender()
        showSpinnerVillage()

        view.fdft_edt_posyandu_date_from.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                val datePickerDialogFragment = DateFragment()
                datePickerDialogFragment.onDateSet(onDateFromListener)
                datePickerDialogFragment.show(fragmentManager!!, "DatePicker")
            }
        })

        view.fdft_edt_posyandu_date_until.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition) {
                val datePickerDialogFragment = DateFragment()
                datePickerDialogFragment.onDateSet(onDateUntilListener)
                datePickerDialogFragment.show(fragmentManager!!, "DatePicker")
            }
        })

        view.fdft_btn_reset.setOnClickListener(this)
        view.fdft_btn_filter.setOnClickListener(this)
        view.fdft_iv_close.setOnClickListener(this)
    }

    fun showSpinnerGender() {
        val adapter = SpinnerGenderAdapter(
            activity?.applicationContext!!,
            R.layout.item_spinner_gender,
            HelperGender.listGender
        )
        fdft_spinner_gender.adapter = adapter
        fdft_spinner_gender.dropDownVerticalOffset = 120
        fdft_spinner_gender.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                val genderModel: GenderModel =
                    fdft_spinner_gender.getItemAtPosition(i) as GenderModel
                if (genderModel.genderSingkatanName == null) {
                    genderId = null
                } else {
                    genderId = genderModel.genderSingkatanName
                }
            }
        }
    }

    fun showSpinnerVillage() {
        val dialogListener = activity as DialogFilterToddlerListener
        val listVillage: List<VillageModel> = dialogListener.getListVillageAll()

        val adapter = SpinnerVillageAdapter(
            activity!!.applicationContext,
            R.layout.item_spinner_gender,
            listVillage
        )
        fdft_spinner_village.adapter = adapter
        fdft_spinner_village.dropDownVerticalOffset = 120
        fdft_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel = fdft_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
            onNothingSelected {

            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            fdft_btn_reset -> {
                rootView.fdft_edt_name.text = null
                rootView.fdft_spinner_gender.setSelection(0)
                rootView.fdft_spinner_village.setSelection(0)
                rootView.fdft_edt_posyandu_date_from.text = null
                rootView.fdft_edt_posyandu_date_until.text = null

                genderId = null
                villageId = null
                dateUntil = null
                dateFrom = null
            }
            fdft_btn_filter -> {
                val toddlerFilter = ToddlerModel()
                toddlerFilter.name = rootView.fdft_edt_name.text.toString()
                toddlerFilter.gender = genderId

                val village = VillageModel()
                village.id = villageId
                toddlerFilter.village = village

                if (dateFrom == null && dateUntil == null) {
                    toddlerFilter.posyanduDate = null
                } else if (dateFrom == null || dateUntil == null) {
                    activity?.toast("Semua tanggal harus diisi !!")
                    return
                } else if (dateFrom != null && dateUntil != null) {
                    val timeMillsDateFrom : Long = HelperDate.parseDateDefault(dateFrom.toString()).time
                    val timeMillsDateUntil : Long = HelperDate.parseDateDefault(dateUntil.toString()).time
                    Log.d("DATEPICKER", "$timeMillsDateFrom $timeMillsDateUntil")

                    if (timeMillsDateFrom > timeMillsDateUntil){
                        toast("tanggal invalid")
                        return
                    }

                    toddlerFilter.posyanduDate = dateFrom + "to" + dateUntil
                }

                val dialogListener = activity as DialogFilterToddlerListener
                dialogListener.filterToddlerClassification(toddlerFilter)

                dismiss()
            }
            fdft_iv_close -> dismiss()
        }
    }

    interface DialogFilterToddlerListener {
        fun getListVillageAll(): List<VillageModel>
        fun filterToddlerClassification(toddler: ToddlerModel?)
    }
}
