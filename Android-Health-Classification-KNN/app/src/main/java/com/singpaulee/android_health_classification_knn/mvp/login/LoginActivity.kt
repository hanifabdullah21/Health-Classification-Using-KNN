package com.singpaulee.android_health_classification_knn.mvp.login

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.mvp.main.MainActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.mvp.register.RegisterActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    private var progressDialog: ProgressDialog? = null
    private lateinit var presenter: LoginMvpPresenter<LoginView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        presenter = LoginPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.checkIsLoggedIn()

        login_btn_login.setOnClickListener(this)
        login_btn_register.setOnClickListener(this)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v) {
            login_btn_login -> presenter.onButtonLoginClick(
                login_edt_username.text.toString(), login_edt_password.text.toString()
            )
            login_btn_register -> presenter.onButtonRegisterClick()
        }
    }

    override fun openRegisterActivity() {
        startActivity(intentFor<RegisterActivity>())
    }

    override fun openMainActivity() {
        startActivity(intentFor<MainActivity>())
        finish()
    }

    override fun showLoading() {
        hideLoading()
        progressDialog = LoadingUtil.showLoadingDialog(this)
    }

    override fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun onError(message: String) {
        when(message){
            "HTTP 401 Unauthorized" -> toast("Username/password salah")
            else -> toast(message)
        }
    }

    override fun showMessage(message: String) {
        toast("Error $message")
        Log.e("CONNECTION", message)
    }

    override fun showValidationError(errorCode: Int) {
        when(errorCode){
            AppContants.EMPTY_USERNAME -> {
                login_edt_username.requestFocus()
                login_edt_username.error = "Tidak Boleh Kosong"
                toast("oi error cuk")
            }
            AppContants.EMPTY_PASSWORD -> {
                login_edt_password.requestFocus()
                login_edt_password.error = "Tidak Boleh Kosong"
            }
        }
    }
}
