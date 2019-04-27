package dev.ricardoantolin.cabifystore.remote.responses

import dev.ricardoantolin.cabifystore.remote.entities.RMProductEntity

data class ProductsResponse (
    val products: List<RMProductEntity>
)