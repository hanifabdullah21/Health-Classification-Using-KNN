package com.singpaulee.android_health_classification_knn.mvp.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.helper.sharedpref.SharedPrefManager
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class SettingActivity : AppCompatActivity() {

    lateinit var sharedpref : SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        sharedpref = SharedPrefManager(this)

        sa_edt_k.setText(sharedpref.K.toString())

        sa_btn_save.onClick {
            sharedpref.K = sa_edt_k.text.toString().toInt()
            toast("Nilai K berhasil diubah")
        }
    }
}
