package com.singpaulee.android_health_classification_knn.eventlistener

import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel

interface MotherPregnantItemClickListener {

    fun onEditClickListener(mp: MotherPregnantModel?)

    fun onDeleteClickListener(mpId: Int?)

    fun onItemClickListener(mp: MotherPregnantModel?)
}