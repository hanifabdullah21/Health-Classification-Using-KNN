package com.singpaulee.android_health_classification_knn.mvp.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.mvp.maintoddler.ToddlerMainActivity
import com.singpaulee.android_health_classification_knn.mvp.login.LoginActivity
import com.singpaulee.android_health_classification_knn.mvp.mainpregnant.MainPregnantActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity(), MainMvpView, View.OnClickListener {

    private lateinit var presenter: MainMvpPresenter<MainMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        presenter.onAttach(this)

        main_cv_balita.setOnClickListener(this)
        main_cv_bumil.setOnClickListener(this)
        main_cv_logout.setOnClickListener(this)
    }

    override fun moveToLogin() {
        finish()
        startActivity(intentFor<LoginActivity>())
    }

    override fun moveToMainToddler() {
        startActivity(intentFor<ToddlerMainActivity>())
    }

    override fun moveToMainPregnantMother() {
        startActivity(intentFor<MainPregnantActivity>())
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }

    override fun showMessage(message: String) {

    }

    override fun showValidationError(errorCode: Int) {

    }

    override fun onClick(v: View?) {
        when(v){
            main_cv_balita -> moveToMainToddler()
            main_cv_bumil -> moveToMainPregnantMother()
            main_cv_logout -> presenter.deleteAllSession()
        }
    }
}
