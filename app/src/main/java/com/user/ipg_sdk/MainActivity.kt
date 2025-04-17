package com.user.ipg_sdk

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val openWeb = findViewById<Button>(R.id.btnOpenWeb)

        openWeb.setOnClickListener {
            SDKManager.lunchPaymentView(
                this,
                "eyJhbGciOiJIUzUxMiJ9.eyJtaWQiOiIwMDAwMTg5NCJ9.u1OqIhWsZ0_ceQmdZQyWBrfiP6A5mjACzuxbitRLp1K8DwMF5ehkKEyzSpWtQXyhbqqcyOFHWS-X28zy3hfbMw",
                "OID123456",
                "Order Description",
                "100.00",
                "John Doe",
                "0771234567",
                "test@email.com"
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if (requestCode == SDKManager.paymentRequest && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("paymentResult")
            Log.e("SDK Result", "Received from SDK: $result")
        }
    }
}