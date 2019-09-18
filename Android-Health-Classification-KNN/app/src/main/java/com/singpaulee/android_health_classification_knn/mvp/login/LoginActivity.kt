package com.singpaulee.android_health_classification_knn.mvp.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.MainActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    internal lateinit var presenter: LoginMvpPresenter<LoginView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(CompositeDisposable())
        presenter.onAttach(this)

        login_btn_login.setOnClickListener(this)
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
        }
    }

    override fun openMainActivity() {
        startActivity(intentFor<MainActivity>())
    }

    override fun showLoading() {
        toast("Show Loading")
    }

    override fun hideLoading() {
        toast("Hide Loading")
    }

    override fun onError(message: String) {
        toast(message)
        Log.e("CONNECTION", message)
    }

    override fun showMessage(message: String) {
        toast("Error $message")
        Log.e("CONNECTION", message)
    }
}
