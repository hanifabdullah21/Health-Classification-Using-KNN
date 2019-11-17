package com.singpaulee.android_health_classification_knn.mvp.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.mvp.maintoddler.ToddlerMainActivity
import com.singpaulee.android_health_classification_knn.mvp.login.LoginActivity
import com.singpaulee.android_health_classification_knn.mvp.mainpregnant.MainPregnantActivity
import com.singpaulee.android_health_classification_knn.mvp.motherpregnanttraining.MotherPregnantTrainingActivity
import com.singpaulee.android_health_classification_knn.mvp.setting.SettingActivity
import com.singpaulee.android_health_classification_knn.mvp.toddlertraining.ToddlerTrainingActivity
import com.singpaulee.android_health_classification_knn.mvp.village.VillageActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.yesButton

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
        main_cv_setting.setOnClickListener(this)
        ma_iv_toddler_training.setOnClickListener(this)
        ma_iv_toddler_test.setOnClickListener(this)
        ma_iv_pregnant_training.setOnClickListener(this)
        ma_iv_pregnant_test.setOnClickListener(this)
        ma_iv_village.setOnClickListener(this)
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
            main_cv_setting -> startActivity(intentFor<SettingActivity>())
            main_cv_logout -> {
                alert("Keluar aplikasi ?") {
                    positiveButton("YA"){
                        presenter.deleteAllSession()
                    }
                    negativeButton("TIDAK"){
                        it.dismiss()
                    }
                }.show()
            }
            ma_iv_toddler_training -> startActivity(intentFor<ToddlerTrainingActivity>("BALITA" to "TRAINING"))
            ma_iv_toddler_test -> startActivity(intentFor<ToddlerTrainingActivity>("BALITA" to "TEST"))
            ma_iv_pregnant_training -> startActivity(intentFor<MotherPregnantTrainingActivity>("PREGNANT" to "TRAINING"))
            ma_iv_pregnant_test -> startActivity(intentFor<MotherPregnantTrainingActivity>("PREGNANT" to "TEST"))
            ma_iv_village -> startActivity(intentFor<VillageActivity>())
        }
    }
}
