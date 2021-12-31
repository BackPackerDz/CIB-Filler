package com.appfiza.cib_filer

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        private const val SEND_SMS_URL = "https://acs.satim.dz/acs/pages/enrollment/authentication"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         *  To test it, replace the value of the variables with your own card data
         *  Go to for example for "https://my.ooredoo.dz/Portal/faces/publicStorm"
         *  And after that when you find yourself in the CIB part
         *  replace "YOUR CIB URL" with the url you were redirected to
         *
         *  Clicking on "Auto-fill" will automatically fill the inputs for you, you'll just have to
         *  put the code you get in the SMS
         */
        val myWebView: WebView = findViewById(R.id.webView)
        myWebView.settings.javaScriptEnabled = true
        myWebView.loadUrl("YOUR CIB URL")


        val cardNumber = "YOUR CARD NUMBER HERE"
        val cvv = "YOUR CVV HER ex 222"
        val month = "10"
        val year = "2023"
        val firstname = "Fayçal"
        val lastname = "Kaddouri"

        val jsFillInputs = "document.getElementById('iPAN').value='$cardNumber';" +
                "document.getElementById('iCVC').value='$cvv';" +
                "document.getElementById('iTEXT').value='${"$lastname $firstname"}';" +
                "document.getElementById('iTEXT').value='${"$lastname $firstname"}';" +
                "document.getElementById('month').value='$month';" +
                "document.getElementById('year').value='$year';"

        val jsClickValidate = "javascript:document.getElementById('buttonPayment').click()"
        val jsClickSendSMS = "javascript:document.getElementById('sendPasswordButton').click()"

        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String) {
                if (url.contains(SEND_SMS_URL)) {
                    myWebView.evaluateJavascript(jsClickSendSMS) {}
                }
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            myWebView.evaluateJavascript(jsFillInputs) {}
            myWebView.evaluateJavascript(jsClickValidate) {}
        }
    }
}