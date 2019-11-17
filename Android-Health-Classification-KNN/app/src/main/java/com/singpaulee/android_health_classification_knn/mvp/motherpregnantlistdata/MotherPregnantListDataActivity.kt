package com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistdata

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.MotherPregnantMasterAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.MotherPregnantItemClickListener
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_mother_pregnant_list_data.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class MotherPregnantListDataActivity : AppCompatActivity(), MotherPregnantListDataMvpView,
    View.OnClickListener, MotherPregnantItemClickListener {

    private var progressDialog: ProgressDialog? = null
    private lateinit var presenter: MotherPregnantListDataMvpPresenter<MotherPregnantListDataMvpView>

    private var villageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_list_data)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MotherPregnantListDataPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListVillage()
        presenter.getListMotherPregnantFilter(villageId, mplda_edt_search_name.text.toString())

        mplda_btn_search.setOnClickListener(this)
    }

    override fun showSpinnerVillage(adapter: SpinnerVillageAdapter) {
        mplda_spinner_village.adapter = adapter
        mplda_spinner_village.dropDownVerticalOffset = 120
        mplda_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel =
                        mplda_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
            onNothingSelected {

            }
        }
    }

    override fun showRecyclerviewMotherPregnant(listData: ArrayList<MotherPregnantModel>?) {
        mplda_rv_toddler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mplda_rv_toddler.adapter = MotherPregnantMasterAdapter(listData, this, this)
    }

    override fun emptyListData(hide: Boolean) {
        if (hide) {
            mplda_rv_toddler.visibility = View.GONE
            mplda_group.visibility = View.VISIBLE
        } else {
            mplda_rv_toddler.visibility = View.VISIBLE
            mplda_group.visibility = View.GONE
        }
    }

    override fun refreshDataMotherPregnant() {
        presenter.getListMotherPregnantFilter(villageId, mplda_edt_search_name.text.toString())
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
        when (v) {
            mplda_btn_search -> presenter.getListMotherPregnantFilter(
                villageId,
                mplda_edt_search_name.text.toString()
            )
        }
    }

    override fun onEditClickListener(mp: MotherPregnantModel?) {
//        startActivity(intentFor<ToddlerUpdateActivity>("TODDLER" to toddler))
    }

    override fun onDeleteClickListener(mpId: Int?) {
        alert("Hapus Data Bumil ?") {
            positiveButton("HAPUS") {
                presenter.deleteMotherPregnantData(mpId)
            }
            negativeButton("BATAL") {
                it.dismiss()
            }
        }.show()
    }

    override fun onItemClickListener(mp: MotherPregnantModel?) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
