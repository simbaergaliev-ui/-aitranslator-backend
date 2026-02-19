package com.aitranslator.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseRequest(
    val productId: String,
    val purchaseToken: String
)


