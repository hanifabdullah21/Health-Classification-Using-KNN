package com.singpaulee.android_health_classification_knn.connection

import com.singpaulee.android_health_classification_knn.model.response.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    /** Base Using Raw*/
    //    @Headers("Content-Type: application/json")
    //    @PUT("....")
    //    fun functionName(
    //        @Header("Authorization") bearer: String?,
    //        @Body body: JsonObject
    //    ): Observable<ResponseBody>

    /* **************************************** AUTH and PROFIL ************************************/

    @FormUrlEncoded
    @POST("auth/register")
    fun registerAccount(
        @Field("email") email: String?,
        @Field("username") username: String?,
        @Field("nama") name: String?,
        @Field("password") password: String?
    ): Observable<AuthResponseModel?>

    @FormUrlEncoded
    @POST("auth/login")
    fun loginAccount(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Observable<AuthResponseModel?>

    /* **************************************** VILLAGE *******************************************/

    @GET("dusun")
    fun getListVillage(
        @Header("Authorization") auth: String?
    ): Observable<VillageResponseModel?>

    /* **************************************** TODDLER *******************************************/

    @FormUrlEncoded
    @POST("balita")
    fun addNewToddler(
        @Header("Authorization") auth: String?,
        @Field("dusun_id") dusunId: Int?,
        @Field("nama") nama: String?,
        @Field("jenis_kelamin") jenisKelamin: String?,
        @Field("tanggal_lahir") tanggalLahir: String?
    ): Observable<ToddlerResponseModel?>

    @GET("balita/filter")
    fun getListBalitaFilter(
        @Header("Authorization") auth: String?,
        @Query("dusun_id") dusunId: Int?,
        @Query("nama") nama: String?
    ): Observable<ToddlerListResponseModel?>

    @GET("balita/training")
    fun getListBalitaTraining(
        @Header("Authorization") auth: String?
    ): Observable<ToddlerListResponseModel?>

    @FormUrlEncoded
    @POST("balita/classification")
    fun postClassification(
        @Header("Authorization") auth: String?,
        @Field("balita_id") balitaId: Int?,
        @Field("umur") age: Int?,
        @Field("tanggal_posyandu") posyanduDate: String?,
        @Field("tinggi_badan") height: Double?,
        @Field("berat_badan") weight: Double?,
        @Field("status") status: String?
    ): Observable<ToddlerResponseModel?>

    @GET("balita/classification")
    fun getClassificationToddler(
        @Header("Authorization") auth: String?,
        @Query("balita_id") toddlerId: Int?,
        @Query("nama") name: String?,
        @Query("jenis_kelamin") gender: String?,
        @Query("dusun_id") villageId: Int?,
        @Query("range_tanggal") rangeDate: String?,
        @Query("status") status: String?
    ): Observable<ToddlerListResponseModel?>

    @GET("bumil/training")
    fun getListBumilTraining(
        @Header("Authorization") auth: String?
    ): Observable<MotherPregnantListResponseModel?>

    @FormUrlEncoded
    @POST("bumil/classification")
    fun addBumilClassification(
        @Header("Authorization") auth: String?,
        @Field("dusun_id") dusunId: Int?,
        @Field("nama") name: String?,
        @Field("usia_bumil") usiaBumil: Int?,
        @Field("usia_kehamilan") usiaKehamilan: Int?,
        @Field("berat_badan") weight: Double?,
        @Field("tinggi_badan") height: Double?,
        @Field("LILA") LILA: Double?,
        @Field("KEK") KEK: Int,
        @Field("status") status: String?,
        @Field("tanggal_posyandu") posyanduDate: String?
    ): Observable<MotherPregnantResponseModel?>

    @GET("bumil/classification")
    fun getClassificationBumil(
        @Header("Authorization") auth: String?,
        @Query("nama") name: String?,
        @Query("dusun_id") villageId: Int?,
        @Query("usia_kehamilan") usiaKehamilan: Int?,
        @Query("status") status: String?
    ): Observable<MotherPregnantListResponseModel?>
}