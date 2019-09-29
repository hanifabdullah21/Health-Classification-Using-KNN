package com.singpaulee.android_health_classification_knn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlinx.android.synthetic.main.item_toddler_classification.view.*

class ToddlerClassificationAdapter(val list: ArrayList<ToddlerModel>?, val context: Context) :
    RecyclerView.Adapter<ToddlerClassificationAdapter.ViewHolder>() {

    lateinit var itemView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemView = LayoutInflater.from(context).inflate(R.layout.item_toddler_classification, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(toddlerModel: ToddlerModel) {
            itemView.itc_tv_name.text = toddlerModel.toddler?.name
            itemView.itc_tv_posyandu_date.text = toddlerModel.posyanduDate
            itemView.itc_tv_age.text = "${toddlerModel.age} bulan"
            itemView.itc_tv_height.text = "${toddlerModel.height} cm"
            itemView.itc_tv_weight.text = "${toddlerModel.weight} kg"
            itemView.itc_tv_status.text = toddlerModel.status
        }
    }
}