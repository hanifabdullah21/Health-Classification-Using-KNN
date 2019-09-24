package com.singpaulee.android_health_classification_knn.mvp.toddlerlistdata

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.adapter.ToddlerMasterAdapter
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_toddler_list_data.*
import kotlinx.android.synthetic.main.activity_toddler_register.*
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class ToddlerListDataActivity : AppCompatActivity(), ToddlerListDataMvpView, View.OnClickListener {

    private var progressDialog: ProgressDialog? = null
    private lateinit var presenter : ToddlerListDataMvpPresenter<ToddlerListDataMvpView>

    private var villageId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_list_data)

        presenter = ToddlerListDataPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListVillage()
        presenter.getListToddlerFilter(villageId, tlda_edt_search_name.text.toString())

        tlda_btn_search.setOnClickListener(this)
    }

    override fun showRecyclerviewToddler(adapter: ToddlerMasterAdapter) {
        tlda_rv_toddler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tlda_rv_toddler.adapter = adapter
    }

    override fun showSpinnerVillage(adapter: SpinnerVillageAdapter) {
        tlda_spinner_village.adapter = adapter
        tlda_spinner_village.dropDownVerticalOffset = 120
        tlda_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel = tlda_spinner_village.getItemAtPosition(i - 1) as VillageModel
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

    override fun onClick(v: View?) {
        when(v){
            tlda_btn_search -> presenter.getListToddlerFilter(villageId, tlda_edt_search_name.text.toString())
        }
    }
}
