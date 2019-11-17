package com.singpaulee.android_health_classification_knn.mvp.mainpregnant

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.mvp.maintoddler.ToddlerMainActivity
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantclassification.MotherPregnantClassificationActivity
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistbeoreclassification.MotherPregnantListBeforeClassificationActivity
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistdata.MotherPregnantListDataActivity
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantregister.MotherPregnantRegisterActivity
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantresultclassification.MotherPregnantResultClassificationActivity
import kotlinx.android.synthetic.main.activity_main_pregnant.*
import org.jetbrains.anko.intentFor

class MainPregnantActivity : AppCompatActivity(), MainPregnantMvpView, View.OnClickListener {

    private lateinit var presenter: MainPregnantMvpPresenter<MainPregnantMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pregnant)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MainPregnantPresenter(this)
        presenter.onAttach(this)

        pma_cv_classification.setOnClickListener(this)
        pma_cv_result_classification.setOnClickListener(this)
        pma_cv_register.setOnClickListener(this)
        pma_cv_data.setOnClickListener(this)
    }

    override fun moveToMotherPregnantClassification() {
        startActivity(intentFor<MotherPregnantListBeforeClassificationActivity>())
    }

    override fun moveToMotherPregnantResultClassification() {
        startActivity(intentFor<MotherPregnantResultClassificationActivity>())
    }

    fun moveToMotherPregnantRegister() {
        startActivity(intentFor<MotherPregnantRegisterActivity>())
    }

    private fun moveToMotherPregnantList() {
        startActivity(intentFor<MotherPregnantListDataActivity>())
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showValidationError(errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        when (v) {
            pma_cv_classification -> moveToMotherPregnantClassification()
            pma_cv_result_classification -> moveToMotherPregnantResultClassification()
            pma_cv_register -> moveToMotherPregnantRegister()
            pma_cv_data -> moveToMotherPregnantList()
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
