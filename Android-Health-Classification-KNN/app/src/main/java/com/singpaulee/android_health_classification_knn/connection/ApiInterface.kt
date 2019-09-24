package com.singpaulee.android_health_classification_knn.connection

import com.singpaulee.android_health_classification_knn.model.base.VillageModel
import com.singpaulee.android_health_classification_knn.model.response.AuthResponseModel
import com.singpaulee.android_health_classification_knn.model.response.ToddlerResponseModel
import com.singpaulee.android_health_classification_knn.model.response.VillageResponseModel
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

}