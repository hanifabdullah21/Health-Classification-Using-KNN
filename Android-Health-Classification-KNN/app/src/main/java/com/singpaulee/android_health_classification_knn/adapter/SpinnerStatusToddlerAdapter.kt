package com.singpaulee.android_health_classification_knn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.singpaulee.android_health_classification_knn.R
import kotlinx.android.synthetic.main.item_spinner_dropdown_gender.view.*
import kotlinx.android.synthetic.main.item_spinner_gender.view.*

class SpinnerStatusToddlerAdapter(
    context: Context, private val layoutResource: Int,
    private val listGender: List<String>
) :
    ArrayAdapter<String>(context, layoutResource, listGender) {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val customView = LayoutInflater.from(context).inflate(layoutResource, null)
        customView.isg_gender_name.text = listGender[position]
        return customView
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listGender.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val customView =
            LayoutInflater.from(context).inflate(R.layout.item_spinner_dropdown_gender, null)
        customView.isdg_tv_name.text = listGender[position]
        return customView
    }
}