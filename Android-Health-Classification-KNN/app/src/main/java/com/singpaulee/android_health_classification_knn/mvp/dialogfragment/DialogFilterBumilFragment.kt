package com.singpaulee.android_health_classification_knn.mvp.dialogfragment


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerStatusToddlerAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.DrawableClickListener
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import kotlinx.android.synthetic.main.fragment_dialog_filter_bumil.view.*
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.*
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.*
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.fdft_btn_filter
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.fdft_btn_reset
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.fdft_edt_name
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.fdft_iv_close
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.fdft_spinner_status
import kotlinx.android.synthetic.main.fragment_dialog_filter_toddler.view.fdft_spinner_village
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DialogFilterBumilFragment : DialogFragment(), View.OnClickListener {

    lateinit var rootView: View

    var villageId: Int? = null
    var status: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dialog_filter_bumil, container, false)
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

        showSpinnerVillage()
        showSpinnerStatus()

        view.fdft_btn_reset.setOnClickListener(this)
        view.fdft_btn_filter.setOnClickListener(this)
        view.fdft_iv_close.setOnClickListener(this)
    }

    fun showSpinnerVillage() {
        val dialogListener = activity as DialogFilterBumilListener
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

    fun showSpinnerStatus() {
        val statusList = listOf<String>(
            "Silahkan pilih status",
            AppContants.STATUS_PREGNANT.STATUS_KURANG.status,
            AppContants.STATUS_PREGNANT.STATUS_NORMAL.status,
            AppContants.STATUS_PREGNANT.STATUS_OVERWEIGHT.status,
            AppContants.STATUS_PREGNANT.STATUS_OBESITAS.status
        )

        val adapter = SpinnerStatusToddlerAdapter(
            activity!!.applicationContext,
            R.layout.item_spinner_gender,
            statusList
        )

        fdft_spinner_status.adapter = adapter
        fdft_spinner_status.dropDownVerticalOffset = 120
        fdft_spinner_status.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    status = null
                } else {
                    status = fdft_spinner_status.getItemAtPosition(i).toString()
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
                rootView.fdft_edt_age_pregnant.text = null
                rootView.fdft_spinner_village.setSelection(0)
                rootView.fdft_spinner_status.setSelection(0)

                villageId = null
            }
            fdft_btn_filter -> {
                val pm = MotherPregnantModel()
                pm.nama = rootView.fdft_edt_name.text.toString()

                if (!rootView.fdft_edt_age_pregnant.text.isNullOrBlank() || !rootView.fdft_edt_age_pregnant.text.isNullOrEmpty())
                    pm.usiaKehamilan = rootView.fdft_edt_age_pregnant.text.toString().toInt()


                val village = VillageModel()
                village.id = villageId
                pm.dusun = village
                pm.dusunId = villageId

                pm.status = status

                val dialogListener = activity as DialogFilterBumilListener
                dialogListener.filterPMClassification(pm)

                dismiss()
            }
            fdft_iv_close -> dismiss()
        }
    }

    interface DialogFilterBumilListener {
        fun getListVillageAll(): List<VillageModel>
        fun filterPMClassification(pm: MotherPregnantModel?)
    }
}
