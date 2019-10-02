package com.singpaulee.android_health_classification_knn.mvp.toddlerresultclassification

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.PercentageToddlerActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.ToddlerClassificationAdapter
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.dialogfragment.DialogFilterToddlerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_toddler_result_classification.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onTouch
import org.jetbrains.anko.toast

class ToddlerResultClassificationActivity : AppCompatActivity(),
    ToddlerResultClassificationMvpView, DialogFilterToddlerFragment.DialogFilterToddlerListener {

    lateinit var presenter: ToddlerResultClassificationMvpPresenter<ToddlerResultClassificationMvpView>
    private var progressDialog: ProgressDialog? = null

    var listAllVillage: List<VillageModel>? = null

    var listClassification: ArrayList<ToddlerModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_result_classification)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = ToddlerResultClassificationPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListClassification(ToddlerModel())
        presenter.getListVillage()

        trca_edt_search.onTouch { v, event ->
            openDialogFilter()
        }

        trca_btn_filter.onClick {
            openDialogFilter()
        }

        trca_btn_presentase.onClick {
            startActivity(intentFor<PercentageToddlerActivity>("listClassification" to listClassification))
        }
    }

    override fun openDialogFilter() {
        val dialogFragment: DialogFragment = DialogFilterToddlerFragment()
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")
    }

    override fun showListClassificationToddler(listClassificationToddler: ArrayList<ToddlerModel>?) {
        listClassification = listClassificationToddler
        trca_rv_result.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ToddlerClassificationAdapter(listClassificationToddler, this)
        trca_rv_result.adapter = adapter
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

    override fun showListVillage(listVillage: List<VillageModel>?) {
        listAllVillage = listVillage
    }

    override fun getListVillageAll(): List<VillageModel> {
        return listAllVillage ?: listOf()
    }

    override fun filterToddlerClassification(toddler: ToddlerModel?) {
        presenter.getListClassification(toddler)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
