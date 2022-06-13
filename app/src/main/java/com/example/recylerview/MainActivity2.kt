package com.example.recylerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    lateinit var fb: FloatingActionButton
   private  lateinit var recview: RecyclerView
   private lateinit var adapter: myadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
       //  fb = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener {
            startActivity(Intent(applicationContext, uploadfile::class.java))
        }
        recview.layoutManager = LinearLayoutManager(this)
        val options: FirebaseRecyclerOptions<mymodel> = FirebaseRecyclerOptions.Builder<mymodel>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("mydocuments"),
                mymodel::class.java
            )
            .build()

        adapter = myadapter(options)
        recview.adapter = adapter


    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()

    }
}