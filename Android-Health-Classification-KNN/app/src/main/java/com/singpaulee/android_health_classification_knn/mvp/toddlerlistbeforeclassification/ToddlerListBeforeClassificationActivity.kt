package com.singpaulee.android_health_classification_knn.mvp.toddlerlistbeforeclassification

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.adapter.ToddlerBeforeClassificationAdapter
import com.singpaulee.android_health_classification_knn.adapter.ToddlerMasterAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.ToddlerItemClickListener
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_toddler_list_before_classification.*
import kotlinx.android.synthetic.main.activity_toddler_list_data.*
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class ToddlerListBeforeClassificationActivity : AppCompatActivity(),
    ToddlerListBeforeClassificationMvpView, View.OnClickListener, ToddlerItemClickListener {

    private var progressDialog: ProgressDialog? = null
    private lateinit var presenter: ToddlerListBeforeClassificationMvpPresenter<ToddlerListBeforeClassificationMvpView>

    private var villageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_list_before_classification)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = ToddlerListBeforeClassificationPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListVillage()
        presenter.getListToddlerFilter(villageId, tlbca_edt_search_name.text.toString())

        tlbca_btn_search.setOnClickListener(this)
    }

    override fun showRecyclerviewToddler(listData: ArrayList<ToddlerModel>?) {
        tlbca_rv_toddler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tlbca_rv_toddler.adapter = ToddlerBeforeClassificationAdapter(listData, this, this)
    }

    override fun showSpinnerVillage(adapter: SpinnerVillageAdapter) {
        tlbca_spinner_village.adapter = adapter
        tlbca_spinner_village.dropDownVerticalOffset = 120
        tlbca_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel =
                        tlbca_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
            onNothingSelected {

            }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun emptyListData(hide: Boolean) {
        if (hide) {
            tlbca_rv_toddler.visibility = View.GONE
            tlbca_group.visibility = View.VISIBLE
        } else {
            tlbca_rv_toddler.visibility = View.VISIBLE
            tlbca_group.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            tlbca_btn_search -> presenter.getListToddlerFilter(
                villageId,
                tlbca_edt_search_name.text.toString()
            )
        }
    }

    override fun onEditClickListener(toddler: ToddlerModel?) {

    }

    override fun onDeleteClickListener(toddlerId: Int?) {

    }

    override fun onItemClickListener(toddler: ToddlerModel?) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
