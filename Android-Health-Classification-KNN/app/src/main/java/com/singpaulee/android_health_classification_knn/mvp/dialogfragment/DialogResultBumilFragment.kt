package com.singpaulee.android_health_classification_knn.mvp.dialogfragment


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlinx.android.synthetic.main.fragment_dialog_result.view.*
import kotlinx.android.synthetic.main.fragment_dialog_result_bumil.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * A simple [Fragment] subclass.
 */
class DialogResultBumilFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dialog_result_bumil, container, false)
        isCancelable = false
        return rootView
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mother: MotherPregnantModel? = arguments?.getParcelable("mother")
        view.drbf_tv_name.text = mother?.nama.toString()
        view.drbf_tv_age.text = "${mother?.usiaBumil} tahun"
        view.drbf_tv_pregnant_age.text = "${mother?.usiaKehamilan} bulan"
        view.drbf_tv_height.text = "${mother?.tinggiBadan} cm"
        view.drbf_tv_weight.text = "${mother?.beratBadan} kg"
        view.drbf_tv_lila.text = "${mother?.lILA}"
        view.drbf_tv_kek.text = if(mother?.kEK==1) "YA" else "TIDAK"
        view.drbf_tv_posyandu_date.text = mother?.posyanduDate
        view.drbf_tv_status.text = mother?.status

        view.drbf_btn_back_to_main_menu.onClick {
            val dialogListener = activity as DialogResultFragmentListener
            dialogListener.backToMainMenu()
            dismiss()
        }
    }

    interface DialogResultFragmentListener {
        fun backToMainMenu()
    }
}
