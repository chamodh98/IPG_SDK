package com.user.ipg_sdk

/**
 * Created by Chamod Hettiarachchi on 2025-04-16
 */

data class PaymentData (
    var packageName: String = "",
    var paymentMethod: String = "",
    var merchantWebToken: String = "",
    var orderId: String = "",
    var orderDescription: String = "",
    var returnUrl: String = "",
    var cancelUrl: String = "",
    var subMerchantReference: String = "",
    var totalAmount: String = "",
    var customerName: String = "",
    var customerPhone: String = "",
    var customerEmail: String = ""
)