package com.user.ipg_sdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Chamod Hettiarachchi on 2025-04-16
 */

@Parcelize
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
    var customerMobile: String = "",
    var customerEmail: String = ""
) : Parcelable