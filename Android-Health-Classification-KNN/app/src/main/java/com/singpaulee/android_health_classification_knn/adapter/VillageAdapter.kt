package com.singpaulee.android_health_classification_knn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import kotlinx.android.synthetic.main.item_village.view.*

class VillageAdapter(var list: List<VillageModel>?) :
    RecyclerView.Adapter<VillageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_village, parent, false
        )
    )

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun changeListItem(newList: List<VillageModel>?){
        list = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.iv_tv_name.text = list?.get(position)?.name.toString()
        }
    }

}