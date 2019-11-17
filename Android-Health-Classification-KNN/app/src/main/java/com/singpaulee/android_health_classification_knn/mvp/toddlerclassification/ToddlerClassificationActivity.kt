package com.singpaulee.android_health_classification_knn.mvp.toddlerclassification

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.mvp.dialogfragment.DialogResultFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_toddler_classification.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class ToddlerClassificationActivity : AppCompatActivity(), ToddlerClassificationMvpView, DialogResultFragment.DialogResultFragmentListener {

    var toddler: ToddlerModel? = null

    private lateinit var presenter: ToddlerClassificationMvpPresenter<ToddlerClassificationMvpView>
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_classification)

        toddler = intent.getParcelableExtra("toddler")
        setContent()

        presenter = ToddlerClassificationPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListDataTraining(toddler?.gender)
        toast(toddler?.gender.toString())

        tca_btn_classification.onClick {
            if (!presenter.validationInput(tca_edt_height.text.toString(), tca_edt_weight.text.toString())){
                toast("error")
                return@onClick
            }
            toddler?.height = tca_edt_height.text.toString().toDouble()
            toddler?.weight = tca_edt_weight.text.toString().toDouble()
            toddler?.posyanduDate = HelperDate.getCurrentDateFormatDefault()

            presenter.classificationToddler(toddler, SharedPrefManager(this@ToddlerClassificationActivity).K)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setContent() {
        tca_edt_name.setText(toddler?.name)
        tca_edt_gender.setText(toddler?.gender)
        tca_edt_age.setText("${toddler?.age} bulan")
        tca_edt_village.setText(toddler?.village?.name)
        tca_edt_date_posyandu.setText(HelperDate.getCurrentDateFormatDmy())
    }

    @SuppressLint("SetTextI18n")
    override fun showSuccessGetDataTraining(success: Boolean) {
        if (success) {
            tca_iv_indicator_success.visibility = View.VISIBLE
            tca_tv_indicator_message.visibility = View.VISIBLE
            tca_tv_indicator_try_again.visibility = View.GONE
            tca_iv_indicator_success.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            tca_tv_indicator_message.text = "Data Training Tersedia"
        } else {
            tca_iv_indicator_success.visibility = View.VISIBLE
            tca_tv_indicator_message.visibility = View.VISIBLE
            tca_tv_indicator_try_again.visibility = View.VISIBLE
            tca_iv_indicator_success.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            tca_tv_indicator_message.text = "Data Training Tidak Tersedia"
        }
    }

    override fun showLoading() {
        hideLoading()
        progressDialog = LoadingUtil.showLoadingDialog(this)
    }

    override fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun onError(message: String) {
        toast(message)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showValidationError(errorCode: Int) {

    }

    override fun showResultClassification(toddler: ToddlerModel?) {
        Log.d("RESULT_CLASSIFICATION", toddler.toString())

        val dialogFragment = DialogResultFragment()
        val bundle = Bundle()
        bundle.putParcelable("toddler", toddler)
        dialogFragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.isCancelable = false
        dialogFragment.show(ft, "dialog")
    }

    override fun backToMainMenu() {
        finish()
    }
}
