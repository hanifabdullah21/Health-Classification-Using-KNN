package com.singpaulee.android_health_classification_knn.helper

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


class PermissionUtil {

    fun checkPermissionReadExternalStorage(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(WRITE_EXTERNAL_STORAGE), 1
            )
            return
        }
    }

}