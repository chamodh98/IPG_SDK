package com.user.ipg_sdk

import android.app.Activity
import android.content.Intent

/**
 * Created by Chamod Hettiarachchi on 2025-04-16
 */

class IPGSDKManager {
    companion object {
        const val paymentRequest = 999

        fun lunchPaymentView(
            activity: Activity,
            merchantWebToken: String,
            environment: SDKEnvironment,
            orderId: String,
            orderDescription: String,
            totalAmount: String,
            customerName: String,
            customerMobile: String,
            customerEmail: String
        ) {
            val intent = Intent(activity, ActWebView::class.java).apply {
                putExtra(
                    "paymentData",
                    setPaymentData(
                        merchantWebToken,
                        orderId,
                        orderDescription,
                        totalAmount,
                        customerName,
                        customerMobile,
                        customerEmail
                    )
                )
                putExtra(
                    "webUrl",
                    setSDKUrl(environment)
                )
            }
            activity.startActivityForResult(intent, paymentRequest)
        }

        private fun setPaymentData(
            merchantWebToken: String,
            orderId: String,
            orderDescription: String,
            totalAmount: String,
            customerName: String,
            customerMobile: String,
            customerEmail: String
        ): PaymentData {
            return PaymentData(
                merchantWebToken = merchantWebToken,
                orderId = orderId,
                orderDescription = orderDescription,
                totalAmount = totalAmount,
                customerName = customerName,
                customerMobile = customerMobile,
                customerEmail = customerEmail
            )
        }

        private fun setSDKUrl(environment: SDKEnvironment): String {
            return when (environment) {
                SDKEnvironment.LIVE -> "https://ipay.lk/ipg/checkout/sdk/android"
                SDKEnvironment.DEVELOPMENT -> "https://developer.ipay.lk/ipg/checkout/sdk/android"
                SDKEnvironment.STAGING -> "https://staging.ipay.lk/ipg/checkout/sdk/android"
                SDKEnvironment.SANDBOX -> "https://sandbox.ipay.lk/ipg/checkout/sdk/android"
            }
        }
    }
}