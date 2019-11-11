package com.singpaulee.android_health_classification_knn.mvp.toddlerrpercentage

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.singpaulee.android_health_classification_knn.R
import com.singpaulee.android_health_classification_knn.helper.AppContants
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import kotlinx.android.synthetic.main.activity_percentage_toddler.*


class PercentageToddlerActivity : AppCompatActivity() {

    var listClassification: ArrayList<ToddlerModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_percentage_toddler)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listClassification = intent.getParcelableArrayListExtra("listClassification")

        setPieChart(listClassification)

        setBarChart(listClassification)
    }

    private fun setBarChart(listClassification: ArrayList<ToddlerModel>?) {
        val listClassificationGroup: Map<String?, List<ToddlerModel>> =
            listClassification!!.groupBy { it.status }
        val statusBuruk =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_BURUK.status]?.size ?: 0
        val statusBaik =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_BAIK.status]?.size ?: 0
        val statusKurang =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_KURANG.status]?.size ?: 0
        val statusLebih =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_LEBIH.status]?.size ?: 0
        val statusObesitas =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_OBESITAS.status]?.size ?: 0

        val colors: ArrayList<Int> = ArrayList()
        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        //Set Data Entries
        val entriesBuruk: ArrayList<BarEntry> =
            mutableListOf(BarEntry(0.toFloat(), statusBuruk.toFloat())) as ArrayList<BarEntry>
        val barDataSetBuruk = BarDataSet(entriesBuruk, "Buruk [$statusBuruk]")
        barDataSetBuruk.color = colors[0]

        val entriesKurang: ArrayList<BarEntry> =
            mutableListOf(BarEntry(1.toFloat(), statusKurang.toFloat())) as ArrayList<BarEntry>
        val barDataSetKurang = BarDataSet(entriesKurang, "Kurang [$statusKurang]")
        barDataSetKurang.color = colors[1]

        val entriesBaik: ArrayList<BarEntry> =
            mutableListOf(BarEntry(2.toFloat(), statusBaik.toFloat())) as ArrayList<BarEntry>
        val barDataSetBaik = BarDataSet(entriesBaik, "Baik [$statusBaik]")
        barDataSetBaik.color = colors[2]

        val entriesLebih: ArrayList<BarEntry> =
            mutableListOf(BarEntry(3.toFloat(), statusLebih.toFloat())) as ArrayList<BarEntry>
        val barDataSetLebih = BarDataSet(entriesLebih, "Lebih [$statusLebih]")
        barDataSetLebih.color = colors[3]

        val entriesObesitas: ArrayList<BarEntry> =
            mutableListOf(BarEntry(4.toFloat(), statusObesitas.toFloat())) as ArrayList<BarEntry>
        val barDataSetObesitas = BarDataSet(entriesObesitas, "Obesitas [$statusObesitas]")
        barDataSetObesitas.color = colors[4]

        val barData = BarData()
        barData.addDataSet(barDataSetBuruk)
        barData.addDataSet(barDataSetKurang)
        barData.addDataSet(barDataSetBaik)
        barData.addDataSet(barDataSetLebih)
        barData.addDataSet(barDataSetObesitas)

        pta_barchart.description.isEnabled = false
        pta_barchart.setPinchZoom(false)
        pta_barchart.setDrawBarShadow(false)
        pta_barchart.setDrawGridBackground(false)

        val xAxis = pta_barchart.getXAxis()
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
//        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IAxisValueFormatter { value, axis -> "" }

        val leftAxis = pta_barchart.axisLeft
        leftAxis.valueFormatter = LargeValueFormatter()
//        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 50f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        pta_barchart.axisRight.isEnabled = false

        pta_barchart.data = barData
        pta_barchart.invalidate()

    }

    private fun setPieChart(listClassification: ArrayList<ToddlerModel>?) {
        val listClassificationGroup: Map<String?, List<ToddlerModel>> =
            listClassification!!.groupBy { it.status }
        val statusBuruk =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_BURUK.status]?.size ?: 0
        val statusBaik =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_BAIK.status]?.size ?: 0
        val statusKurang =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_KURANG.status]?.size ?: 0
        val statusLebih =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_LEBIH.status]?.size ?: 0
        val statusObesitas =
            listClassificationGroup[AppContants.STATUS_MODE.STATUS_OBESITAS.status]?.size ?: 0

        pta_piechart.setUsePercentValues(true)
        pta_piechart.description.isEnabled = false
        pta_piechart.setExtraOffsets(5F, 10F, 5F, 5F)

        pta_piechart.dragDecelerationFrictionCoef = 0.95F

        pta_piechart.isDrawHoleEnabled = true
        pta_piechart.setHoleColor(Color.WHITE)

        pta_piechart.setTransparentCircleColor(Color.WHITE)
        pta_piechart.setTransparentCircleAlpha(110)

        pta_piechart.holeRadius = 40F
        pta_piechart.transparentCircleRadius = 50F

        pta_piechart.setDrawCenterText(true)

        pta_piechart.rotationAngle = 0F

        pta_piechart.isRotationEnabled = true
        pta_piechart.isHighlightPerTapEnabled = true

        pta_piechart.animateY(1400, Easing.EaseInOutQuad)

        val l = pta_piechart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f
        l.textSize = 12f


        // entry label styling
        pta_piechart.setEntryLabelColor(Color.BLACK)
        pta_piechart.setEntryLabelTextSize(16f)

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(
            PieEntry(
                statusBuruk.toFloat(),
                AppContants.STATUS_MODE.STATUS_BURUK.status,
                null
            )
        )
        entries.add(
            PieEntry(
                statusKurang.toFloat(),
                AppContants.STATUS_MODE.STATUS_KURANG.status,
                null
            )
        )
        entries.add(
            PieEntry(
                statusBaik.toFloat(),
                AppContants.STATUS_MODE.STATUS_BAIK.status,
                null
            )
        )
        entries.add(
            PieEntry(
                statusLebih.toFloat(),
                AppContants.STATUS_MODE.STATUS_LEBIH.status,
                null
            )
        )
        entries.add(
            PieEntry(
                statusObesitas.toFloat(),
                AppContants.STATUS_MODE.STATUS_OBESITAS.status,
                null
            )
        )

        val dataSet = PieDataSet(entries, "Status Klasifikasi")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 10f                         //Jarak antara potongan status
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 10f                    //Ukuran ketika potongan dipilih

        val colors: ArrayList<Int> = ArrayList()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.BLACK)
        pta_piechart.data = data

        // undo all highlights
        pta_piechart.highlightValues(null)

        pta_piechart.invalidate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
