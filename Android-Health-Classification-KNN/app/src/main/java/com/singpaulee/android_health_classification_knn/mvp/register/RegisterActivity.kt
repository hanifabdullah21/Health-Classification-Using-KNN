package com.singpaulee.android_health_classification_knn.mvp.register

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.helper.AppContants.EMPTY_CONFIRM_PASSWORD
import com.singpaulee.android_health_classification_knn.helper.AppContants.EMPTY_EMAIL
import com.singpaulee.android_health_classification_knn.helper.AppContants.EMPTY_NAME
import com.singpaulee.android_health_classification_knn.helper.AppContants.EMPTY_PASSWORD
import com.singpaulee.android_health_classification_knn.helper.AppContants.EMPTY_USERNAME
import com.singpaulee.android_health_classification_knn.helper.AppContants.FALSE_CONFIRM_PASSWORD
import com.singpaulee.android_health_classification_knn.helper.AppContants.FALSE_EMAIL_FORMAT
import com.singpaulee.android_health_classification_knn.helper.AppContants.FALSE_USERNAME_FORMAT
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), RegisterMvpView {

    lateinit var presenter: RegisterMvpPresenter<RegisterMvpView>
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        reg_btn_register.onClick {
            presenter.registerNewAccount(
                reg_edt_name.text.toString(),
                reg_edt_email.text.toString(),
                reg_edt_username.text.toString(),
                reg_edt_password.text.toString(),
                reg_edt_confirm_password.text.toString()
            )
        }
    }

    override fun resetAllView() {
        toast("Akun berhasil dibuat")
        reg_edt_name.text = null
        reg_edt_username.text = null
        reg_edt_email.text = null
        reg_edt_password.text = null
        reg_edt_confirm_password.text = null
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
            EMPTY_NAME -> {
                reg_edt_name.requestFocus()
                reg_edt_name.error = "Tidak Boleh Kosong"
            }
            EMPTY_EMAIL -> {
                reg_edt_email.requestFocus()
                reg_edt_email.error = "Tidak Boleh Kosong"
            }
            FALSE_EMAIL_FORMAT -> {
                reg_edt_email.requestFocus()
                reg_edt_email.error = "Format email salah"
            }
            EMPTY_USERNAME -> {
                reg_edt_username.requestFocus()
                reg_edt_username.error = "Tidak Boleh Kosong"
            }
            FALSE_USERNAME_FORMAT -> {
                reg_edt_username.requestFocus()
                reg_edt_username.error = "Format username salah"
            }
            EMPTY_PASSWORD -> {
                reg_edt_password.requestFocus()
                reg_edt_password.error = "Tidak Boleh Kosong"
            }
            EMPTY_CONFIRM_PASSWORD -> {
                reg_edt_confirm_password.requestFocus()
                reg_edt_confirm_password.error = "Tidak Boleh Kosong"
            }
            FALSE_CONFIRM_PASSWORD -> {
                reg_edt_confirm_password.requestFocus()
                reg_edt_confirm_password.error = "Konfirmasi Password Tidak Sesuai"
            }
        }
    }
}
