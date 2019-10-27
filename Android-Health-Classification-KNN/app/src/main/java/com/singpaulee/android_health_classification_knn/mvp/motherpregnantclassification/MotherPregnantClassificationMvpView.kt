package com.singpaulee.android_health_classification_knn.mvp.motherpregnantclassification

import com.singpaulee.android_health_classification_knn.adapter.SpinnerVillageAdapter
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.mvp.base.MvpView

interface MotherPregnantClassificationMvpView : MvpView {

    fun showSuccessGetDataTraining(success: Boolean)

    fun showResultClassification(pregnantMother: MotherPregnantModel?)

    fun showSpinnerVillage(adapter: SpinnerVillageAdapter)

}