package com.user.ipg_sdk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

internal class ActWebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.act_web_view)
        val webView : WebView = findViewById(R.id.webView)
        val closeButton: Button = findViewById(R.id.closeButton)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient= WebViewClient()
        webView.addJavascriptInterface(WebAppInterface(this),"JSInterface")

        val paymentData: PaymentData? = intent.getParcelableExtra("paymentData")
        val url = "https://developer.ipay.lk/ipg/checkout/sdk/android"
        webView.loadUrl(url)

        val escapedToken = paymentData?.merchantWebToken?.replace("'", "\\'")
        val jsCode = """
                initSdkCheckout(
                    'com.ipay.mobile.sdk',
                    '${escapedToken}',
                    '${paymentData?.totalAmount}',
                    '${paymentData?.orderId}',
                    '${paymentData?.orderDescription}',
                    '',
                    '${paymentData?.customerName}',
                    '${paymentData?.customerPhone}',
                    '${paymentData?.customerEmail}',
                    '',
                    ''
                )
                """

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(jsCode, null) // Load the JavaScript code after the page is loaded
            }
        }

        closeButton.setOnClickListener {
            finish()
        }
    }

    inner class WebAppInterface(private val activity: ActWebView) {

        @JavascriptInterface
        fun sdkSuccessCallback(transactionStatus: String, transactionMessage: String, transactionReference: String) {
            activity.runOnUiThread {
                Toast.makeText(activity,"Received -: $transactionStatus $transactionMessage $transactionReference", Toast.LENGTH_LONG).show()
                var resultIntent = Intent()
                resultIntent.putExtra("transactionStatus", transactionStatus)
                resultIntent.putExtra("transactionMessage", transactionMessage)
                resultIntent.putExtra("transactionReference", transactionReference)
                activity.setResult(RESULT_OK, resultIntent)
                activity.finish()
            }
        }
    }
}