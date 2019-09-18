package com.singpaulee.android_health_classification_knn.connection

import com.singpaulee.android_health_classification_knn.model.response.AuthResponseModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    /** Base Using Raw*/
    //    @Headers("Content-Type: application/json")
    //    @PUT("....")
    //    fun functionName(
    //        @Header("Authorization") bearer: String?,
    //        @Body body: JsonObject
    //    ): Observable<ResponseBody>


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

}