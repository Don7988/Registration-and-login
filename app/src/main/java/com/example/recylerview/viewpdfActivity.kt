package com.example.recylerview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class viewpdfActivity : AppCompatActivity() {
    var listView: ListView? = null

    //database reference to get uploads data
    var mDatabaseReference: DatabaseReference? = null

    //list to store uploads data
  lateinit var uploadList: List<UploadPdfModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpdf2)


        uploadList = ArrayList()
        listView = findViewById<View>(R.id.listview) as ListView
        listView!!.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                val upload = uploadList[i]

                //Opening the upload file in
                val intent = Intent(Intent.ACTION_VIEW)
                //default app using url
                intent.setDataAndType(Uri.parse(upload.getFilename()), "application/pdf")
                startActivity(intent)
            }




        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads")

        //retrieving upload data from firebase database

        //retrieving upload data from firebase database
        mDatabaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(UploadPdfModel::class.java)
                     (uploadList as ArrayList<UploadPdfModel>).add(upload!!)
                }
                val uploads = arrayOfNulls<String>(uploadList.size)
                for (i in uploads.indices) {
                    uploads[i] = uploadList[i].getFileurl()
                }

                //displaying it to list
                val adapter = ArrayAdapter(
                    applicationContext, android.R.layout.simple_list_item_1, uploads
                )
                listView!!.adapter = adapter
            }

            override fun onCancelled(e: DatabaseError) {
                Toast.makeText(this@viewpdfActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}



