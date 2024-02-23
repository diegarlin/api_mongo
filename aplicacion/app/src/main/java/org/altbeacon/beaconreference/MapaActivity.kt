package org.altbeacon.beaconreference

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapaActivity : Activity() {

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mapa_activity)

        val webView: WebView = findViewById(R.id.webView)


        // Configuración del WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
                }
        }

        webView.loadUrl("https://ap.cs.ucy.ac.cy/viewer/?buid=building_3f91a8fd-ca7b-4d7e-bc04-626e2623d229_1704557723480&floor=0")


    }
}
