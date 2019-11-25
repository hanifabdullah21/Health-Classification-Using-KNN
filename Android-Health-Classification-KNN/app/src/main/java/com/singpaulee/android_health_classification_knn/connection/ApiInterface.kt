package com.singpaulee.android_health_classification_knn.connection

import com.singpaulee.android_health_classification_knn.model.base.MotherPregnantModel
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

    @FormUrlEncoded
    @POST("dusun")
    fun addNewVillage(
        @Header("Authorization") auth: String?,
        @Field("nama") name: String?
    ): Observable<VillageResponseObjectModel>

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

    @FormUrlEncoded
    @POST("balita/delete")
    fun deleteToddler(
        @Header("Authorization") auth: String?,
        @Field("balita_id") toddlerId: Int?
    ): Observable<ToddlerResponseModel?>

    @FormUrlEncoded
    @POST("balita/update")
    fun updateToddler(
        @Header("Authorization") auth: String?,
        @Field("dusun_id") dusunId: Int?,
        @Field("nama") nama: String?,
        @Field("jenis_kelamin") jenisKelamin: String?,
        @Field("tanggal_lahir") tanggalLahir: String?,
        @Field("balita_id") toddlerId: Int?
    ): Observable<ToddlerResponseModel?>

    @GET("balita/filter")
    fun getListBalitaFilter(
        @Header("Authorization") auth: String?,
        @Query("dusun_id") dusunId: Int?,
        @Query("nama") nama: String?
    ): Observable<ToddlerListResponseModel?>

    @FormUrlEncoded
    @POST("balita/training")
    fun addBalitaTraining(
        @Header("Authorization") auth: String?,
        @Field("umur") age:Int?,
        @Field("tinggi_badan") height: Double?,
        @Field("berat_badan") weight: Double?,
        @Field("jenis_kelamin") gender: String?,
        @Field("status") status: String?
    ): Observable<ToddlerResponseModel?>

    @GET("balita/training")
    fun getListBalitaTraining(
        @Header("Authorization") auth: String?,
        @Query("jenis_kelamin") gender: String?
    ): Observable<ToddlerListResponseModel?>

    @FormUrlEncoded
    @POST("balita/test")
    fun addBalitaTest(
        @Header("Authorization") auth: String?,
        @Field("umur") age:Int?,
        @Field("tinggi_badan") height: Double?,
        @Field("berat_badan") weight: Double?,
        @Field("jenis_kelamin") gender: String?,
        @Field("status") status: String?
    ): Observable<ToddlerResponseModel?>

    @GET("balita/test")
    fun getListBalitaTest(
        @Header("Authorization") auth: String?,
        @Query("jenis_kelamin") gender: String?
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

    /* **************************************** BUMIL *******************************************/

    @FormUrlEncoded
    @POST("bumil")
    fun addNewBumil(
        @Header("Authorization") auth: String?,
        @Field("dusun_id") dusunId: Int?,
        @Field("nama") nama: String?,
        @Field("tanggal_lahir") tanggalLahir: String?,
        @Field("tanggal_kehamilan") tanggalHamil: String?
    ): Observable<MotherPregnantResponseModel?>

    @FormUrlEncoded
    @POST("bumil/delete")
    fun deleteBumil(
        @Header("Authorization") auth: String?,
        @Field("bumil_id") bumilId: Int?
    ): Observable<MotherPregnantResponseModel?>

    @FormUrlEncoded
    @POST("bumil/update")
    fun updateBumil(
        @Header("Authorization") auth: String?,
        @Field("dusun_id") dusunId: Int?,
        @Field("nama") nama: String?,
        @Field("tanggal_lahir") tanggalLahir: String?,
        @Field("tanggal_kehamilan") tanggalHamil: String?,
        @Field("bumil_id") bumilId: Int?
    ): Observable<MotherPregnantResponseModel?>

    @GET("bumil/filter")
    fun getListBumilFilter(
        @Header("Authorization") auth: String?,
        @Query("dusun_id") dusunId: Int?,
        @Query("nama") nama: String?
    ): Observable<MotherPregnantListResponseModel?>

    @GET("bumil/training")
    fun getListBumilTraining(
        @Header("Authorization") auth: String?
    ): Observable<MotherPregnantListResponseModel?>

    @FormUrlEncoded
    @POST("bumil/training")
    fun addBumilTraining(
        @Header("Authorization") auth: String?,
        @Field("nama") name: String?,
        @Field("usia_bumil") usiaBumil: Int?,
        @Field("usia_kehamilan") usiaKehamilan: Int?,
        @Field("berat_badan") weight: Double?,
        @Field("tinggi_badan") height: Double?,
        @Field("status") status: String?
    ): Observable<MotherPregnantResponseModel?>

    @GET("bumil/test")
    fun getListBumilTest(
        @Header("Authorization") auth: String?
    ): Observable<MotherPregnantListResponseModel?>

    @FormUrlEncoded
    @POST("bumil/test")
    fun addBumilTest(
        @Header("Authorization") auth: String?,
        @Field("nama") name: String?,
        @Field("usia_bumil") usiaBumil: Int?,
        @Field("usia_kehamilan") usiaKehamilan: Int?,
        @Field("berat_badan") weight: Double?,
        @Field("tinggi_badan") height: Double?,
        @Field("status") status: String?
    ): Observable<MotherPregnantResponseModel?>

    @FormUrlEncoded
    @POST("bumil/classification")
    fun addBumilClassification(
        @Header("Authorization") auth: String?,
        @Field("dusun_id") dusunId: Int?,
        @Field("bumil_id") bumilId: Int?,
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