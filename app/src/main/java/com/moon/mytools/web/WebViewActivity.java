package com.moon.mytools.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moon.common.ui.component.MBaseActivity;
import com.moon.mytools.R;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.ycbjie.webviewlib.base.X5WebChromeClient;
import com.ycbjie.webviewlib.client.JsX5WebViewClient;
import com.ycbjie.webviewlib.view.X5WebView;

/**
 * Date: 2021/10/18 1:59 下午
 * Author: Moon
 * Desc: WebView页面
 */
public class WebViewActivity extends MBaseActivity {

    private X5WebView mWebView;
    private TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    private void initView() {
        mWebView = ((X5WebView) findViewById(R.id.wv_show));
        findViewById(R.id.tv_back).setOnClickListener(v -> finish());
        mTvTitle = ((TextView) findViewById(R.id.tv_title));

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        mWebView.setWebViewClient(new MWebViewClient(mWebView, this));
        mWebView.setWebChromeClient(new MWebChromeClient(mWebView, this));
    }

    private static class MWebViewClient extends JsX5WebViewClient {

        private final Context context;

        /**
         * 构造方法
         *
         * @param webView 需要传进来webview
         * @param context 上下文
         */
        public MWebViewClient(X5WebView webView, Context context) {
            super(webView, context);
            this.context = context;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
            url = url.toLowerCase();
            if (AdFilterTool.hasAd(context, url)) {
                return new WebResourceResponse(null, null, null);
            }
            return super.shouldInterceptRequest(webView, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            // 屏蔽部分广告
            try {
                view.loadUrl("javascript:" +
                        "function removeThem(){" +
                        "document.querySelector('.cm465174+a').style.display=\"none\";" +
                        "document.querySelector('.cm465174_qq').style.display=\"none\";" +
                        "document.querySelector('.swiper-container').style.display=\"none\";" +
                        "document.querySelector('.swiper-wrapper').style.display=\"none\";" +
                        "document.querySelector('.swiper-container').nextElementSibling.style.display=\"none\";" +
                        "document.querySelector('.plist.plist1').getElementsByTagName('div')[0].style.display=\"none\";" +
                        "} " +
                        "removeThem();");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class MWebChromeClient extends X5WebChromeClient {

        /**
         * 构造方法
         *
         * @param webView
         * @param context 上下文
         */
        public MWebChromeClient(WebView webView, Context context) {
            super(webView, context);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void open(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}
