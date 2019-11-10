package com.singpaulee.android_health_classification_knn.mvp.motherpregnantresultclassification

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.MotherPregnantClassificationAdapter
import com.singpaulee.android_health_classification_knn.adapter.ToddlerClassificationAdapter
import com.singpaulee.android_health_classification_knn.helper.ExcelUtil
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.PermissionUtil
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.dialogfragment.DialogFilterBumilFragment
import com.singpaulee.android_health_classification_knn.mvp.dialogfragment.DialogFilterToddlerFragment
import com.singpaulee.android_health_classification_knn.mvp.motherpregnantpercentage.MotherPregnantPercentageActivity
import com.singpaulee.android_health_classification_knn.mvp.toddlerrpercentage.PercentageToddlerActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_mother_pregnant_result_classification.*
import kotlinx.android.synthetic.main.activity_toddler_result_classification.*
import org.apache.poi.ss.usermodel.Workbook
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onTouch
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MotherPregnantResultClassificationActivity : AppCompatActivity(),
    MotherPregnantResultClassificationMvpView, DialogFilterBumilFragment.DialogFilterBumilListener {

    lateinit var presenter: MotherPregnantResultClassificationMvpPresenter<MotherPregnantResultClassificationMvpView>
    private var progressDialog: ProgressDialog? = null

    var listAllVillage: List<VillageModel>? = null

    var listClassification: ArrayList<MotherPregnantModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother_pregnant_result_classification)

        PermissionUtil().checkPermissionReadExternalStorage(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MotherPregnantResultClassificationPresenter(CompositeDisposable(), this)
        presenter.onAttach(this)

        presenter.getListClassification(MotherPregnantModel())
        presenter.getListVillage()

        mprca_edt_search.onTouch { v, event ->
            openDialogFilter()
        }

        mprca_btn_filter.onClick {
            openDialogFilter()
        }

        mprca_btn_presentase.onClick {
            startActivity(intentFor<MotherPregnantPercentageActivity>("listClassification" to listClassification))
        }

        mprca_btn_print_to_excel.onClick {
            val workbook = ExcelUtil().createExcelBumilClassification(listClassification)
            saveExcelToMyDirectory(workbook)
        }
    }

    private fun saveExcelToMyDirectory(workbook: Workbook) {
        val fileName =
            "Presentase Klasifikasi Bumil ${HelperDate.getCurrentDateFormatDefaultWithTime().replace(":","-")}.xlsx"
        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
        val folder = File(extStorageDirectory, "Klasifikasi KNN")
        folder.mkdir()
        val file = File(folder, fileName)
        try {
            file.createNewFile()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        try {
            val fileOut = FileOutputStream(file)
            workbook.write(fileOut)
            fileOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.d("PATHFILE", extStorageDirectory)

        toast("File disimpan di $extStorageDirectory")
    }

    override fun openDialogFilter() {
        val dialogFragment: DialogFragment = DialogFilterBumilFragment()
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")
    }

    override fun showListClassificationBumil(listClassification: ArrayList<MotherPregnantModel>?) {
        this.listClassification = listClassification
        mprca_rv_result.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MotherPregnantClassificationAdapter(this.listClassification, this)
        mprca_rv_result.adapter = adapter
    }

    override fun showListVillage(listVillage: List<VillageModel>?) {
        listAllVillage = listVillage
    }

    override fun hideListClassification(hide: Boolean) {
        if (hide) {
            mprca_rv_result.visibility = View.GONE
            mprca_btn_presentase.visibility = View.GONE

            mprca_group.visibility = View.VISIBLE
        } else {
            mprca_rv_result.visibility = View.VISIBLE
            mprca_btn_presentase.visibility = View.VISIBLE

            mprca_group.visibility = View.GONE
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

    }

    override fun getListVillageAll(): List<VillageModel> {
        return listAllVillage ?: listOf()
    }

    override fun filterPMClassification(pm: MotherPregnantModel?) {
        presenter.getListClassification(pm)
    }
}
