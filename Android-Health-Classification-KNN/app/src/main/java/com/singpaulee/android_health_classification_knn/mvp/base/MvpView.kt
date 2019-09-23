package com.singpaulee.android_health_classification_knn.mvp.base


interface MvpView {

    /** Menampilkan loading
     *
     * */
    fun showLoading()

    /** Menyembunyika loading
     *
     * */
    fun hideLoading()

    /** Menampilkan pesan kesalahan (failure)
     *
     * */
    fun onError(message: String)

    /** Menampilkan pesan bisa pesan kesalahan atau pesan yang lain
     *
     * */
    fun showMessage(message: String)

    /** Menampilkan pesan kesalahan karena gagal validasi
     *
     * */
    fun showValidationError(errorCode: Int)

}