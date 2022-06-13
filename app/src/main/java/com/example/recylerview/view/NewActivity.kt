package com.example.recylerview.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recylerview.R
import com.example.recylerview.uitel.getProgessDrawable
import com.example.recylerview.uitel.loadImage
import com.example.recylerview.viewpdfActivity
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)






        val animalIntent = intent
        val animalName = animalIntent.getStringExtra("name")
        val animalInfo = animalIntent.getStringExtra("info")
        val animalImg = animalIntent.getStringExtra("img")

        /**call text and images*/
        name.text = animalName
        info.text = animalInfo
        img.loadImage(animalImg, getProgessDrawable(this))
        pdfViewer.setOnClickListener {
            val intent = Intent(this@NewActivity, viewpdfActivity::class.java)
            startActivity(intent)

        }
    }

}