package dev.ricardoantolin.cabifystorage.remote.services.products

import dev.ricardoantolin.cabifystorage.remote.responses.ProductsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface APIProductsService {
    @GET("bins/4bwec")
    fun fetchProducts(): Single<ProductsResponse>
}