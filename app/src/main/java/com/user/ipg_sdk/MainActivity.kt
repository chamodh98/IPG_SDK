package com.user.ipg_sdk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val openWeb = findViewById<Button>(R.id.btnOpenWeb)

        openWeb.setOnClickListener {
            IPGSDKManager.lunchPaymentView(
                this,
                "eyJhbGciOiJIUzUxMiJ9.eyJhcHBsaWNhdGlvblR5cGUiOiJBTkRST0lEIiwibWlkIjoiMDAwMDE4OTQifQ.Oi-n6HcVvn9JFzbIyBBcEn4uffDgYxOKyfK6HAE0RE9T_PDlz8wTKn5Q1ui-n3yt5RtWQ3k3CxQLVhDTQXaOaQ",
                "OID123456",
                "Order Description",
                "250.25",
                "John Doe",
                "0771234567",
                "test@email.com"
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IPGSDKManager.paymentRequest && resultCode == RESULT_OK) {
            val transactionStatus = data?.getStringExtra("transactionStatus")
            val transactionMessage = data?.getStringExtra("transactionMessage")
            val transactionReference = data?.getStringExtra("transactionReference")
            Toast.makeText(this,"Received -: $transactionStatus $transactionMessage $transactionReference", Toast.LENGTH_LONG).show()
            Log.e("SDK Result", "Received from SDK: transactionStatus - $transactionStatus , transactionMessage - $transactionMessage - transactionReference - $transactionReference")
        }
    }
}