package com.singpaulee.android_health_classification_knn.mvp.dialogfragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DateFragment() : DialogFragment() {
    lateinit var listener: DatePickerDialog.OnDateSetListener
    val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val yy = calendar.get(Calendar.YEAR)
        val mm = calendar.get(Calendar.MONTH)
        val dd = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity!!, listener, yy, mm, dd)
    }

    fun onDateSet(dateListener: DatePickerDialog.OnDateSetListener){
        listener = dateListener
    }
}