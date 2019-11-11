package com.singpaulee.android_health_classification_knn.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.mvp.login.LoginActivity
import org.jetbrains.anko.intentFor

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {
            finish()
            startActivity(intentFor<LoginActivity>())
        },2000)
    }
}
