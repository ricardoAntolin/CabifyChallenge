package dev.ricardoantolin.cabifystorage.remote.responses

import dev.ricardoantolin.cabifystorage.remote.models.RMProductEntity

data class ProductsResponse (
    val products: List<RMProductEntity>
)