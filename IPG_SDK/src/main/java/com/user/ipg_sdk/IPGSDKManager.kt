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
            orderId: String,
            orderDescription: String,
            totalAmount: String,
            customerName: String,
            customerPhone: String,
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
                            customerPhone,
                            customerEmail
                        )
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
            customerPhone: String,
            customerEmail: String
        ): PaymentData {
            return PaymentData(
                merchantWebToken = merchantWebToken,
                orderId = orderId,
                orderDescription = orderDescription,
                totalAmount = totalAmount,
                customerName = customerName,
                customerPhone = customerPhone,
                customerEmail = customerEmail
            )
        }
    }
}