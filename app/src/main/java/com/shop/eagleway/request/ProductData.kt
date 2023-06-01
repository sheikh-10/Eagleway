package com.shop.eagleway.request

import com.shop.eagleway.response.MeasuringUnit

data class ProductData(
    val name: String? = null,
    val salesPrice: Int? = null,
    val mrp: Int? = null,
    val quantity: Int? = null,
    val userNum: String? = null,
    val productId: String? = null,
    val category: String? = null,
    val currency: String? = null,
    val measuringUnit: String? = null,
    val description: String? = null
)
