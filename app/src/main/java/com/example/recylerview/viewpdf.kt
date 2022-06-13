package com.example.recylerview

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.net.URLEncoder


class viewpdf : AppCompatActivity() {

   private lateinit var pdfview: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpdf)

        pdfview.getSettings().setJavaScriptEnabled(true);
        val filename = intent.getStringExtra("filename")
        val fileurl = intent.getStringExtra("fileurl")
        val pd = ProgressDialog(this)
        pd.setTitle(filename);
        pd.setMessage("Opening....!!!");
        pdfview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                pd.show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                pd.dismiss()
            }
        }
        var url = ""
        try {
            url = URLEncoder.encode(fileurl, "UTF-8")
        } catch (ex: Exception) {
        }
        pdfview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);



    }

}