package com.moon.mytools

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebView
import com.ycbjie.webviewlib.base.X5WebChromeClient
import com.ycbjie.webviewlib.client.JsX5WebViewClient
import com.ycbjie.webviewlib.inter.VideoWebListener
import com.ycbjie.webviewlib.view.X5WebView
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Author: LuXin
 * Date: 9/7/21
 * Desc: WebView 页面
 */
class WebViewActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "WebViewActivity.class"
    }

    private val mWebView: X5WebView by lazy { webView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url: String? = intent.getStringExtra("url")
        if (url != null) {
            val settings = webView.settings
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.setAppCacheEnabled(true)
            settings.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            webView.webViewClient = MyWebViewClient(webView, this)
            val myWebChromeClient = MyWebChromeClient(webView, this)
            myWebChromeClient.setVideoWebListener(mVideoWebListener)
            webView.webChromeClient = myWebChromeClient
            webView.loadUrl(url)
        }
    }

    class MyWebViewClient(webView: X5WebView?, context: Context?) :
        JsX5WebViewClient(webView, context) {

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
        }
    }

    class MyWebChromeClient(webView: WebView?, context: Context?) :
        X5WebChromeClient(webView, context) {

        override fun onShowCustomView(
            view: View?,
            customViewCallback: IX5WebChromeClient.CustomViewCallback?
        ) {
            super.onShowCustomView(view, customViewCallback)
            Log.d(TAG, "onShowCustomView")
        }

        override fun onHideCustomView() {
            super.onHideCustomView()
            Log.d(TAG, "onHideCustomView")
        }

        override fun getVideoLoadingProgressView(): View {
            Log.d(TAG, "getVideoLoadingProgressView")
            return super.getVideoLoadingProgressView()
        }
    }

    private val mVideoWebListener = object : VideoWebListener {
        override fun showVideoFullView() {
            Log.d(TAG, "showVideoFullView")
        }

        override fun hindVideoFullView() {
            Log.d(TAG, "hindVideoFullView")
        }

        override fun showWebView() {
            Log.d(TAG, "showWebView")
        }

        override fun hindWebView() {
            Log.d(TAG, "hindWebView")
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (mWebView.pageCanGoBack()) {
                mWebView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
