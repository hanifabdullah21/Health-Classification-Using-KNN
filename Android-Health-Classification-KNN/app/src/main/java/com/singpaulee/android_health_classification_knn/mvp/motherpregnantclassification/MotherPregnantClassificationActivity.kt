package com.singpaulee.android_health_classification_knn.mvp.motherpregnantclassification

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.dialogfragment.DialogResultBumilFragment
import com.singpaulee.android_health_classification_knn.mvp.main.MainActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_mother_pregnant_classification.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class MotherPregnantClassificationActivity : AppCompatActivity(),
    MotherPregnantClassificationMvpView, DialogResultBumilFragment.DialogResultFragmentListener {

    private lateinit var presenter: MotherPregnantClassificationMvpPresenter<MotherPregnantClassificationMvpView>
    private var progressDialog: ProgressDialog? = null

    var villageId: Int? = null

    var motherPregnantModel: MotherPregnantModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_classification)

        motherPregnantModel = intent.getParcelableExtra("mp")
        initView()

        presenter = MotherPregnantClassificationPresenter(this, CompositeDisposable())
        presenter.onAttach(this)

        presenter.getListDataTraining()

        mpca_btn_classification.onClick {
            if (!(presenter.validationInput(
                    mpca_edt_name.text.toString(),
                    mpca_edt_age.text.toString(),
                    mpca_edt_pregnant_age.text.toString(),
                    villageId,
                    mpca_edt_height.text.toString(),
                    mpca_edt_weight.text.toString(),
                    mpca_edt_lila.text.toString()
                ))
            ) {
                return@onClick
            }

            val mother = MotherPregnantModel(
                id = motherPregnantModel?.id,
                nama = mpca_edt_name.text.toString(),
                usiaBumil = mpca_edt_age.text.toString().toInt(),
                usiaKehamilan = mpca_edt_pregnant_age.text.toString().toInt(),
                dusunId = villageId,
                tinggiBadan = mpca_edt_height.text.toString().toDouble(),
                beratBadan = mpca_edt_weight.text.toString().toDouble(),
                lILA = mpca_edt_lila.text.toString().toDouble(),
                posyanduDate = HelperDate.getCurrentDateFormatDefault()
            )

            presenter.classificationMotherPregnant(mother, SharedPrefManager(this@MotherPregnantClassificationActivity).K)
        }
    }

    private fun initView() {
        mpca_edt_name.setText(motherPregnantModel?.nama.toString())
        val age = motherPregnantModel?.umur!!/12
        mpca_edt_age.setText(age.toString())
        villageId = motherPregnantModel?.dusunId
        mpca_edt_village.setText(motherPregnantModel?.dusun?.name.toString())
        mpca_edt_date_posyandu.setText(HelperDate.getCurrentDateFormatDmy())
    }

    override fun showSuccessGetDataTraining(success: Boolean) {
        if (success) {
            mpca_iv_indicator_success.visibility = View.VISIBLE
            mpca_tv_indicator_message.visibility = View.VISIBLE
            mpca_tv_indicator_try_again.visibility = View.GONE
            mpca_iv_indicator_success.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            mpca_tv_indicator_message.text = "Data Training Tersedia"
        } else {
            mpca_iv_indicator_success.visibility = View.VISIBLE
            mpca_tv_indicator_message.visibility = View.VISIBLE
            mpca_tv_indicator_try_again.visibility = View.VISIBLE
            mpca_iv_indicator_success.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            mpca_tv_indicator_message.text = "Data Training Tidak Tersedia"
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
        when (errorCode) {
            AppContants.EMPTY_NAME -> {
                mpca_edt_name.requestFocus()
                mpca_edt_name.error = "Tidak boleh kosong"
            }
            AppContants.EMPTY_AGE -> {
                mpca_edt_age.requestFocus()
                mpca_edt_age.error = "Tidak boleh kosong"
            }
            AppContants.EMPTY_PREGNANT_AGE -> {
                mpca_edt_pregnant_age.requestFocus()
                mpca_edt_pregnant_age.error = "Tidak boleh kosong"
            }
            AppContants.EMPTY_VILLAGE -> {
                toast("Desa harus diisi")
            }
            AppContants.EMPTY_WEIGHT -> {
                mpca_edt_weight.requestFocus()
                mpca_edt_weight.error = "Tidak boleh kosong"
            }
            AppContants.EMPTY_HEIGHT -> {
                mpca_edt_height.requestFocus()
                mpca_edt_height.error = "Tidak boleh kosong"
            }
            AppContants.EMPTY_LILA -> {
                mpca_edt_lila.requestFocus()
                mpca_edt_lila.error = "Tidak boleh kosong"
            }
        }
    }

    override fun showResultClassification(pregnantMother: MotherPregnantModel?) {
        Log.d("RESULT_CLASSIFICATION", pregnantMother.toString())

        val dialogFragment = DialogResultBumilFragment()
        val bundle = Bundle()
        bundle.putParcelable("mother", pregnantMother)
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
        finishAffinity()
        startActivity(intentFor<MainActivity>())
    }
}
