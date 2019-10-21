package com.singpaulee.android_health_classification_knn.mvp.toddlerresultclassification

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.mvp.toddlerrpercentage.PercentageToddlerActivity
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.ToddlerClassificationAdapter
import com.singpaulee.android_health_classification_knn.helper.ExcelUtil
import com.singpaulee.android_health_classification_knn.helper.HelperDate
import com.singpaulee.android_health_classification_knn.helper.LoadingUtil
import com.singpaulee.android_health_classification_knn.helper.PermissionUtil
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.mvp.dialogfragment.DialogFilterToddlerFragment
import io.reactivex.disposables.CompositeDisposable
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

class ToddlerResultClassificationActivity : AppCompatActivity(),
    ToddlerResultClassificationMvpView, DialogFilterToddlerFragment.DialogFilterToddlerListener {

    lateinit var presenter: ToddlerResultClassificationMvpPresenter<ToddlerResultClassificationMvpView>
    private var progressDialog: ProgressDialog? = null

    var listAllVillage: List<VillageModel>? = null

    var listClassification: ArrayList<ToddlerModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toddler_result_classification)

        PermissionUtil().checkPermissionReadExternalStorage(this)

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

        trca_btn_print_to_excel.onClick {
            val workbook = ExcelUtil().createExcelToddlerClassification(listClassification)
            saveExcelToMyDirectory(workbook)
        }
    }

    private fun saveExcelToMyDirectory(workbook: Workbook) {
        val fileName =
            "Presentase Klasifikasi Balita ${HelperDate.getCurrentDateFormatDefaultWithTime()}.xlsx"
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

    override fun hideListClassification(hide: Boolean) {
        if (hide) {
            trca_rv_result.visibility = View.GONE
            trca_btn_presentase.visibility = View.GONE

            trca_group.visibility = View.VISIBLE
        } else {
            trca_rv_result.visibility = View.VISIBLE
            trca_btn_presentase.visibility = View.VISIBLE

            trca_group.visibility = View.GONE
        }
    }
}
