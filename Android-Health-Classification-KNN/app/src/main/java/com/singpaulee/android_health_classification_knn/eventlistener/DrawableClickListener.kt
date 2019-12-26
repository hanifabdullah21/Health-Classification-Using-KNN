package com.singpaulee.android_health_classification_knn.eventlistener

interface DrawableClickListener {

    enum class DrawablePosition { TOP, BOTTOM, LEFT, RIGHT }
    fun onClick(target: DrawablePosition)
}