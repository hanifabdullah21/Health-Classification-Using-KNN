package com.singpaulee.android_health_classification_knn.helper

import java.text.SimpleDateFormat
import java.util.*

object HelperDate {

    val LOCALE_IN = "in"
    val LOCALE_ID = "ID"

    val SIMPLE_DATE_FORMAT_DEFAULT = "yyyy-MM-dd"
    val SIMPLE_DATE_FORMAT_DMY = "dd-MM-yyyy"

    fun getCurrentDateFormatDefault() : String{
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat(SIMPLE_DATE_FORMAT_DEFAULT)
        return sdf.format(date)
    }

    fun getCurrentDateFormatDmy() : String{
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat(SIMPLE_DATE_FORMAT_DMY)
        return sdf.format(date)
    }

    fun dateFormatDefault(date: Date) : String{
        val sdf = SimpleDateFormat(
            SIMPLE_DATE_FORMAT_DEFAULT,
            Locale(LOCALE_IN, LOCALE_ID)
        )
        return  sdf.format(date)
    }

    fun dateFormatDmy(date: Date) : String{
        val sdf = SimpleDateFormat(
            SIMPLE_DATE_FORMAT_DMY,
            Locale(LOCALE_IN, LOCALE_ID)
        )
        return  sdf.format(date)
    }

    fun parseDateDefault(date: String) : Date{
        val sdf = SimpleDateFormat(
            SIMPLE_DATE_FORMAT_DEFAULT,
            Locale(LOCALE_IN, LOCALE_ID)
        )
        return sdf.parse(date)
    }
}