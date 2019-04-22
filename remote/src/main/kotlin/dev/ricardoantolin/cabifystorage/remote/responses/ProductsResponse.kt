package dev.ricardoantolin.cabifystorage.remote.responses

import dev.ricardoantolin.cabifystorage.remote.entities.RMProductEntity

data class ProductsResponse (
    val products: List<RMProductEntity>
)