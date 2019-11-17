package com.singpaulee.android_health_classification_knn.mvp.motherpregnantlistbeoreclassification

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.MotherPregnantBeforeClassificationAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.eventlistener.MotherPregnantItemClickListener
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantclassification.MotherPregnantClassificationActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_mother_pregnant_list_before_classification.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.toast

class MotherPregnantListBeforeClassificationActivity : AppCompatActivity(),
    MotherPregnantListBeforeClassificationMvpView, View.OnClickListener,
    MotherPregnantItemClickListener {
    override fun showSpinnerVillage(adapter: SpinnerVillageAdapter) {
        mplbca_spinner_village.adapter = adapter
        mplbca_spinner_village.dropDownVerticalOffset = 120
        mplbca_spinner_village.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                if (i == 0) {
                    villageId = null
                } else {
                    val villageModel =
                        mplbca_spinner_village.getItemAtPosition(i - 1) as VillageModel
                    villageId = villageModel.id
                }
            }
            onNothingSelected {

            }
        }
    }

    override fun showRecyclerviewMotherPregnant(listData: ArrayList<MotherPregnantModel>?) {
        mplbca_rv_bumil.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mplbca_rv_bumil.adapter = MotherPregnantBeforeClassificationAdapter(listData, this, this)
    }

    override fun emptyListData(hide: Boolean) {
        if (hide) {
            mplbca_rv_bumil.visibility = View.GONE
            mplbca_group.visibility = View.VISIBLE
        } else {
            mplbca_rv_bumil.visibility = View.VISIBLE
            mplbca_group.visibility = View.GONE
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
        when (v) {
            mplbca_btn_search -> presenter.getListMotherPregnantFilter(
                villageId,
                mplbca_edt_search_name.text.toString()
            )
        }
    }

    override fun onEditClickListener(mp: MotherPregnantModel?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteClickListener(mpId: Int?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClickListener(mp: MotherPregnantModel?) {
        startActivity(intentFor<MotherPregnantClassificationActivity>("mp" to mp))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private var progressDialog: ProgressDialog? = null
    private lateinit var presenter: MotherPregnantListBeforeClassificationMvpPresenter<MotherPregnantListBeforeClassificationMvpView>

    private var villageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_list_before_classification)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MotherPregnantListBeforeClassificationPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListVillage()
        presenter.getListMotherPregnantFilter(villageId, mplbca_edt_search_name.text.toString())

        mplbca_btn_search.setOnClickListener(this)
    }
}
