package com.singpaulee.android_health_classification_knn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.eventlistener.MotherPregnantItemClickListener
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import kotlinx.android.synthetic.main.item_toddler_master.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MotherPregnantMasterAdapter(
    private val list: ArrayList<MotherPregnantModel>?,
    val context: Context,
    val listener: MotherPregnantItemClickListener
) :
    RecyclerView.Adapter<MotherPregnantMasterAdapter.ViewHolder>() {

    lateinit var itemView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemView = LayoutInflater.from(context).inflate(R.layout.item_toddler_master, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it, listener) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            mp: MotherPregnantModel,
            listener: MotherPregnantItemClickListener
        ) {
            itemView.itm_tv_name.text = mp.nama
            itemView.itm_tv_age.text = "${mp.umur!! / 12} tahun"
            itemView.itm_tv_village.text = mp.dusun?.name

            itemView.itm_btn_edit.onClick {
                listener.onEditClickListener(mp)
            }

            itemView.itm_btn_delete.onClick {
                listener.onDeleteClickListener(mp.id)
            }
        }
    }
}