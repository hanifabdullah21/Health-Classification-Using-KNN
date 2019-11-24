package com.singpaulee.android_health_classification_knn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import kotlinx.android.synthetic.main.item_spinner_dropdown_gender.view.*
import kotlinx.android.synthetic.main.item_spinner_gender.view.*

class SpinnerVillageAdapter(
    context: Context,
    private val layoutResource: Int,
    private val listVillage: List<VillageModel>
) :
    ArrayAdapter<VillageModel>(context, layoutResource, listVillage) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val customView = LayoutInflater.from(context).inflate(layoutResource, null)
        if (position == 0){
            customView.isg_gender_name.text = "Silahkan pilih dusun"
        }else{
            customView.isg_gender_name.text = listVillage[position-1].name
        }
        return customView
    }

    override fun getCount(): Int {
        return listVillage.size+1
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val customView =
            LayoutInflater.from(context).inflate(R.layout.item_spinner_dropdown_gender, null)
        if (position == 0){
            customView.isdg_tv_name.text = "Silahkan pilih desa"
        }else{
            customView.isdg_tv_name.text = listVillage[position-1].name
        }
        return customView
    }
}