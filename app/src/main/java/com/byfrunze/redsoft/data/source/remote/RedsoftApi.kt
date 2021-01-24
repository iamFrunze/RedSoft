package com.byfrunze.redsoft.data.source.remote

import com.byfrunze.redsoft.data.model.Data
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedsoftApi {

    @GET("api/v1/products")
    fun fetchProducts(
        @Query("filter[title]") term: String
    ): Observable<Data>

    @GET("api/v1/products/{productId}")
    fun fetchProductId(@Path("productId") id: Int): Observable<Data>

    @GET("api/v1/categories")
    fun fetchCategories(): Observable<Data>
}