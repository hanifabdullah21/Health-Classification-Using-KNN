package com.singpaulee.android_health_classification_knn.mvp.dialogfragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlinx.android.synthetic.main.fragment_dialog_result.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * A simple [Fragment] subclass.
 */
class DialogResultFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dialog_result, container, false)
        isCancelable = false
        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toddler: ToddlerModel? = arguments?.getParcelable("toddler")
        view.drf_tv_name.text = toddler?.toddler?.name
        view.drf_tv_age.text = "${toddler?.age} bulan"
        view.drf_tv_height.text = "${toddler?.height} cm"
        view.drf_tv_weight.text = "${toddler?.weight} kg"
        view.drf_tv_posyandu_date.text = toddler?.posyanduDate
        view.drf_tv_status.text = toddler?.status

        view.drf_btn_back_to_main_menu.onClick {
            val dialogListener = activity as DialogResultFragmentListener
            dialogListener.backToMainMenu()
            dismiss()
        }
    }

    interface DialogResultFragmentListener {
        fun backToMainMenu()
    }
}
