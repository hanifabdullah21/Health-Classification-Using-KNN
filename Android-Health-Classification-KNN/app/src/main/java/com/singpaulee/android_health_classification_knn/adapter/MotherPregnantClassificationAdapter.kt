package com.singpaulee.android_health_classification_knn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlinx.android.synthetic.main.item_mp_classification.view.*
import kotlinx.android.synthetic.main.item_toddler_classification.view.*

class MotherPregnantClassificationAdapter(val list: ArrayList<MotherPregnantModel>?, val context: Context) :
    RecyclerView.Adapter<MotherPregnantClassificationAdapter.ViewHolder>() {

    lateinit var itemView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemView = LayoutInflater.from(context).inflate(R.layout.item_mp_classification, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mp: MotherPregnantModel) {
            itemView.impc_tv_name.text = mp.nama
            itemView.impc_tv_posyandu_date.text = mp.posyanduDate
            itemView.impc_tv_age_pregnant.text = "Usia Kehamilan:${mp.usiaKehamilan} bulan"
            itemView.impc_tv_age_mother.text = "Usia Bumil:${mp.usiaBumil} tahun"
            itemView.impc_tv_height.text = "Tinggi Badan:${mp.tinggiBadan} cm"
            itemView.impc_tv_weight.text = "Berat Badan:${mp.beratBadan} kg"
            itemView.impc_tv_lila.text = "LILA:${mp.lILA}"

            val kek = if (mp.kEK==1) "YA" else "TIDAK"
            itemView.impc_tv_kek.text = "Resiko KEK:$kek"
            itemView.impc_tv_status.text = mp.status
        }
    }
}