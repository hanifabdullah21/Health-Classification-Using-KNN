package com.singpaulee.android_health_classification_knn.helper

import android.util.Log
import com.google.gson.Gson
import com.singpaulee.android_health_classification_knn.model.base.StatusModel
import com.singpaulee.android_health_classification_knn.model.response.AuthResponseModel
import retrofit2.adapter.rxjava2.HttpException

object FailureHandling {

    private val statusCatch = StatusModel(code = 1, success = false, message = null)
    private val statusIsNotHttpException = StatusModel(code = -1, success = false, message = null)

    fun onFailureRequestHandling(e: Throwable): AuthResponseModel? {
        if (e is HttpException) {
            return try {
                val body = e.response()?.errorBody()
                val bodyString: String? = body?.string()
                val response: AuthResponseModel? =
                    Gson().fromJson(bodyString!!, AuthResponseModel::class.java)

                Log.d("onFailureRequest", "Block Try: ${response.toString()}")

                response
            } catch (ex: Exception) {
                Log.d("onFailureRequest", "Block Catch: ${ex.message}")
                AuthResponseModel(status = statusCatch.copy(message = ex.message), auth = null)
            }
        } else {
            Log.d("onFailureRequest", "e is Not HttpException: ${e.message}")
            return AuthResponseModel(
                status = statusIsNotHttpException.copy(message = e.message),
                auth = null
            )
        }
    }

}