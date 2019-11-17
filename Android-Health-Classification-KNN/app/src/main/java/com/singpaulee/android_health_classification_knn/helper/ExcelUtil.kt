package com.singpaulee.android_health_classification_knn.helper

import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
import com.singpaulee.android_health_classification_knn.model.base.ToddlerModel
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelUtil {
    val NO = "No."
    val NAME = "Nama"
    val GENDER = "Jenis Kelamin"
    val VILLAGE = "Dusun"
    val AGE = "Umur"
    val WEIGHT = "Berat Badan"
    val HEIGHT = "Tinggi Badan"
    val STATUS = "Status Gizi"

    val USIA_BUMIL = "Usia Bumil"
    val USIA_KEHAMILAN = "Usia Kehamilan"
    val LILA = "LILA"
    val KEK = "Resiko KEK"


    fun createExcelToddlerClassification(list: ArrayList<ToddlerModel>?): Workbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Toddler Classification")

        val row = sheet.createRow(0)
        row.createCell(0).setCellValue("" + NO)
        row.createCell(1).setCellValue(NAME)
        row.createCell(2).setCellValue(GENDER)
        row.createCell(3).setCellValue(VILLAGE)
        row.createCell(4).setCellValue(AGE)
        row.createCell(5).setCellValue(WEIGHT)
        row.createCell(6).setCellValue(HEIGHT)
        row.createCell(7).setCellValue(STATUS)

        for (i in 0 until list!!.size) {
            val row = sheet.createRow(i+1)

            row.createCell(0).setCellValue("" + (i + 1))
            row.createCell(1).setCellValue("${list[i].toddler?.name}")
            row.createCell(2).setCellValue("${list[i].toddler?.gender}")
            row.createCell(3).setCellValue("${list[i].toddler?.village?.name}")
            row.createCell(4).setCellValue("${list[i].age}")
            row.createCell(5).setCellValue("${list[i].weight}")
            row.createCell(6).setCellValue("${list[i].height}")
            row.createCell(7).setCellValue("${list[i].status}")
        }
        return workbook
    }

    fun createExcelBumilClassification(list: ArrayList<MotherPregnantModel>?): Workbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Toddler Classification")

        val row = sheet.createRow(0)
        row.createCell(0).setCellValue("" + NO)
        row.createCell(1).setCellValue(NAME)
        row.createCell(2).setCellValue(USIA_BUMIL)
        row.createCell(3).setCellValue(USIA_KEHAMILAN)
        row.createCell(4).setCellValue(VILLAGE)
        row.createCell(5).setCellValue(WEIGHT)
        row.createCell(6).setCellValue(HEIGHT)
        row.createCell(7).setCellValue(LILA)
        row.createCell(8).setCellValue(KEK)
        row.createCell(9).setCellValue(STATUS)

        for (i in 0 until list!!.size) {
            val row = sheet.createRow(i+1)

            row.createCell(0).setCellValue("" + (i + 1))
            row.createCell(1).setCellValue("${list[i].nama}")
            row.createCell(2).setCellValue("${list[i].usiaBumil}")
            row.createCell(3).setCellValue("${list[i].usiaKehamilan}")
            row.createCell(4).setCellValue("${list[i].dusun?.name}")
            row.createCell(5).setCellValue("${list[i].beratBadan}")
            row.createCell(6).setCellValue("${list[i].tinggiBadan}")
            row.createCell(7).setCellValue("${list[i].lILA}")

            val kek = if (list[i].kEK==1) "YA" else "TIDAK"
            row.createCell(8).setCellValue("${kek}")
            row.createCell(9).setCellValue("${list[i].status}")
        }
        return workbook
    }
}