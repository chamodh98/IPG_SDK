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
        webView.addJavascriptInterface(WebAppInterface(this),"AndroidInterface")

        val paymentData = intent.getStringExtra("paymentData") ?: ""
        val url = "http://34.126.78.221:80/ipg/checkout/sdk/init/android"
        Log.e("URL", url )
        Log.e("URL_paymentData", paymentData )

        val js = """
            javascript:(function() {
               fetch('$url', {
                  method: "POST",
                  headers: {
                      "Content-Type": "application/json"
                  },
                  body: '$paymentData'
               })
               .then(response => response.text())
               .then(data => {
                   console.log("Success:", data);
                   document.body.innerHTML = data;
                   AndroidInterface.receiveParams(data);
               })
               .catch((error) => {
                   console.error("Error:", error);
               });
            })()
        """.trimIndent()

        webView.loadUrl("about:blank") // Load a blank page first to avoid loading the URL before the JavaScript is ready
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(js, null) // Load the JavaScript code after the page is loaded
            }
        }

        closeButton.setOnClickListener {
            finish()
        }
    }

    inner class WebAppInterface(private val activity: ActWebView) {

        @JavascriptInterface
        fun receiveParams(param: String) {
            activity.runOnUiThread {
                Toast.makeText(activity,"Received -: $param", Toast.LENGTH_LONG).show()
                var resultIntent = Intent()
                resultIntent.putExtra("paymentResult", param)
                activity.setResult(RESULT_OK, resultIntent)
                activity.finish()
            }
        }
    }
}