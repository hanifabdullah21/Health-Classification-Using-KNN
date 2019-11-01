package com.singpaulee.android_health_classification_knn.mvp.village

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.adapter.VillageAdapter
import com.singpaulee.android_health_classification_knn.connection.ApiInterface
import com.singpaulee.android_health_classification_knn.connection.NetworkConfig
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_village.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class VillageActivity : AppCompatActivity() {

    var layoutManager: LinearLayoutManager? = null
    var adapter : VillageAdapter? = null

    var listVillage : List<VillageModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_village)

        listVillage = emptyList()
        layoutManager = LinearLayoutManager(this)
        adapter = VillageAdapter(listVillage)

        va_rv_village.layoutManager = layoutManager
        va_rv_village.adapter = adapter

        getListVilage()

        va_btn_submit.onClick {
            if (!validation()) {
                return@onClick
            }
            sendNewVillage()
        }
    }

    @SuppressLint("CheckResult")
    private fun sendNewVillage() {
        val token = SharedPrefManager(this).getToken()
        val post = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .addNewVillage(
                "Bearer $token",
                va_edt_name.text.toString()
            )

        post.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast("Berhasil menamba dusun baru")
                getListVilage()
            }, {
                toast("Gagal menambah dusun baru")
            })

    }

    @SuppressLint("CheckResult")
    private fun getListVilage() {
        val token = SharedPrefManager(this).getToken()
        val get = NetworkConfig.retrofitConfig().create(ApiInterface::class.java)
            .getListVillage("Bearer $token")
        get.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listVillage = it?.result
                adapter?.changeListItem(listVillage)
                toast("Berhasil mendapatkan daftar dusun")
            }, {
                toast("Gagal mendapatkan daftar list")
            })
    }

    private fun validation(): Boolean {
        return when {
            va_edt_name.text.toString().isNullOrEmpty() || va_edt_name.text.toString().isNullOrBlank() -> {
                va_edt_name.error = "Mohon diisi"
                va_edt_name.requestFocus()
                false
            }
            else -> true
        }
    }
}
