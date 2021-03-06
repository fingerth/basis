package com.fingerth.basislib.utils.webview

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Build
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fingerth.basislib.withSpecialCharacters
import org.jsoup.Jsoup
import java.lang.ref.WeakReference

class WebSet {
    private var web: WeakReference<WebView>? = null
    fun showHtml(h: String?) {
        web?.get()?.let {
            it.loadDataWithBaseURL("http://www.xxx.com", Jsoup.parse(parseBodyDocument(h), "http://www.xxx.com").run {
                for (el in select("img")) {
                    el.attr("src", el.attr("abs:src"))
                }
                outerHtml()
            }, "text/html", "utf-8", null)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun set(webView: WebView): WebSet {
        web = WeakReference(webView)
        webView.settings.apply {
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSupportZoom(true)
            //            builtInZoomControls = true//设置出现缩放工具
            useWideViewPort = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            loadWithOverviewMode = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mediaPlaybackRequiresUserGesture = false
            }
            loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
            setAppCacheEnabled(true)
        }
        webView.apply {
            webViewClient = MyWebViewClient()
            //webChromeClient = MyWebChromeClient()
        }

        return this
    }

    private class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            if (view?.settings?.loadsImagesAutomatically == false) {
                view.settings?.loadsImagesAutomatically = true
            }
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }

    //    private class MyWebChromeClient : WebChromeClient() {
    //        override fun onProgressChanged(view: WebView?, newProgress: Int) {
    //            super.onProgressChanged(view, newProgress)
    //        }
    //    }


    //处理body片断
    private fun parseBodyDocument(source: String?): String = """  
        <html>
            <head>
                <style type='text/css'>
                   img{width:100% !important;height:auto !important} 
                   body,div,td,th{font-size: 1em;line-height: 2.3em;}
                </style>
                <meta name='viewport' content='width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no'> 
            </head>
            <body style='padding: 10px;' >
                ${source?.withSpecialCharacters() ?: ""}
            </body>
        </html>
    """
}