package com.singpaulee.android_health_classification_knn.mvp.maintoddler

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.mvp.toddleraccurate.ToddlerAccurateActivity
import com.singpaulee.android_health_classification_knn.mvp.toddlerlistbeforeclassification.ToddlerListBeforeClassificationActivity
import com.singpaulee.android_health_classification_knn.mvp.toddlerlistdata.ToddlerListDataActivity
import com.singpaulee.android_health_classification_knn.mvp.toddlerregister.ToddlerRegisterActivity
import com.singpaulee.android_health_classification_knn.mvp.toddlerresultclassification.ToddlerResultClassificationActivity
import kotlinx.android.synthetic.main.activity_toddler_main.*
import org.jetbrains.anko.intentFor

class ToddlerMainActivity : AppCompatActivity(), MainToddlerMvpView, View.OnClickListener {

    private lateinit var presenter: MainToddlerMvpPresenter<MainToddlerMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MainToddlerPresenter(this)
        presenter.onAttach(this)

        tma_cv_registration.setOnClickListener(this)
        tma_cv_list_data.setOnClickListener(this)
        tma_cv_classification.setOnClickListener(this)
        tma_cv_result_classification.setOnClickListener(this)
        tma_iv_test.setOnClickListener(this)
    }

    fun moveToToddlerTest(){
        startActivity(intentFor<ToddlerAccurateActivity>())
    }

    override fun moveToToddlerRegister() {
        startActivity(intentFor<ToddlerRegisterActivity>())
    }

    override fun moveToToddlerData() {
        startActivity(intentFor<ToddlerListDataActivity>())
    }

    override fun moveToToddlerClassification() {
        startActivity(intentFor<ToddlerListBeforeClassificationActivity>())
    }

    override fun moveToToddlerResult() {
        startActivity(intentFor<ToddlerResultClassificationActivity>())
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
        when (v) {
            tma_cv_registration -> moveToToddlerRegister()
            tma_cv_list_data -> moveToToddlerData()
            tma_cv_classification -> moveToToddlerClassification()
            tma_cv_result_classification -> moveToToddlerResult()
            tma_iv_test -> moveToToddlerTest()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
