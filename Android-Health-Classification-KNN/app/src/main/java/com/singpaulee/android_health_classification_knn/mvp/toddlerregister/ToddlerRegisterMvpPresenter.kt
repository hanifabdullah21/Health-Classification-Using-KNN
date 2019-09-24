package com.singpaulee.android_health_classification_knn.mvp.toddlerregister

import com.singpaulee.android_health_classification_knn.mvp.base.MvpPresenter

interface ToddlerRegisterMvpPresenter<V : ToddlerRegisterMvpView> : MvpPresenter<V> {

    fun setSpinnerAdapterGender()

    fun getListVillage()

    fun setDatePickerDialog()

    fun validationNewDataToddler(name: String?, gender: String?, bornDate: String?, villageId: Int?) : Boolean

    fun submitNewDataToddler(name: String?, gender: String?, bornDate: String?, villageId: Int?)

}