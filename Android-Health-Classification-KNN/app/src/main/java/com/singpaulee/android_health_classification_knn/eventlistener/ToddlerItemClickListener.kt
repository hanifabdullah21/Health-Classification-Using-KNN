package com.singpaulee.android_health_classification_knn.eventlistener

import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel

interface ToddlerItemClickListener {

    fun onEditClickListener(toddler: ToddlerModel?)

    fun onDeleteClickListener(toddlerId: Int?)

}