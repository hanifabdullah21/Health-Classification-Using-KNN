package com.singpaulee.android_health_classification_knn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.eventlistener.ToddlerItemClickListener
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlinx.android.synthetic.main.item_toddler_master.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ToddlerMasterAdapter(private val list: ArrayList<ToddlerModel>?, val context: Context, val listener: ToddlerItemClickListener) :
    RecyclerView.Adapter<ToddlerMasterAdapter.ViewHolder>() {

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
            toddler: ToddlerModel,
            listener: ToddlerItemClickListener
        ) {
            itemView.itm_tv_name.text = toddler.name
            itemView.itm_tv_age.text = "${toddler.age} bulan"
            itemView.itm_tv_village.text = toddler.village?.name

            itemView.itm_btn_edit.onClick {
                listener.onEditClickListener(toddler)
            }

            itemView.itm_btn_delete.onClick {
                listener.onDeleteClickListener(toddler.id)
            }
        }
    }
}