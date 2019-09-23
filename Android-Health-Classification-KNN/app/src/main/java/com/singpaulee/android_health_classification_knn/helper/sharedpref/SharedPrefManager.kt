package com.singpaulee.android_health_classification_knn.helper.sharedpref

import android.content.Context
import androidx.core.content.edit

class SharedPrefManager(context: Context) {

    private val PREFERENCE_NAME = "CLASSIFICATION_"
    private val PRIVATE_MODE = 0

    private val IS_LOGGED_IN = "IS_LOGGED_IN"
    private val TOKEN_EXTRA = "TOKEN"

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)

    fun getIsLoggedIn(): Boolean = preference.getBoolean(IS_LOGGED_IN, false)
    fun getToken(): String? = preference.getString(TOKEN_EXTRA, null)

    fun setToken(token: String) {
        preference.edit { putString(TOKEN_EXTRA, token) }
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        preference.edit { putBoolean(IS_LOGGED_IN, true) }
    }
}