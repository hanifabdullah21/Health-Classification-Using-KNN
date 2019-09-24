package com.singpaulee.android_health_classification_knn.mvp.toddlerregister

import android.app.DatePickerDialog
import com.singpaulee.android_health_classification_knn.adapter.SpinnerGenderAdapter
import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface ToddlerRegisterMvpView : MvpView {

    fun showSpinnerGender(adapter: SpinnerGenderAdapter)

    fun showSpinnerVillage(adapter: SpinnerVillageAdapter)

    fun onShowDatePicker(datePickerDialog: DatePickerDialog)

    fun onShowSelectedDate(date: String)

    fun resetAllView()
}