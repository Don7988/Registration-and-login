package com.example.recylerview

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    var selectPdf: ImageView? = null
    var uploadBtn: Button? = null
    var pdfListsBtn: Button? = null
    var editText: EditText? = null
    var storageReference: StorageReference? = null
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        uploadBtn!!.isEnabled = false

        selectPdf = findViewById(R.id.uploadpdf)
        editText = findViewById(R.id.editText)
        uploadBtn = findViewById(R.id.uploadBtn)
        pdfListsBtn = findViewById(R.id.pdflist)


        // AFTER CLICKING ON pdfListsBtn BUTTON WE WILL REDIRECTED SHOW PDF FILES ACTIVITY
        pdflist.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity3, viewpdfActivity::class.java)
            startActivity(intent)
        })
    }
    var dialog: ProgressDialog? = null


    // OVERRIDE A METHOD onActivityResult METHOD WHICH REDIRECT PDF FILE SELECTED SUCCESSFULLY
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            dialog = ProgressDialog(this)
            if (editText!!.text.toString().isEmpty()) {
                editText!!.error = "Required"
            } else {
                uploadBtn!!.isEnabled = true
            }
            // AFTER CLICKING ON upload BUTTON WE WILL REDIRECTED TO UPLOAD PDF FILES METHOD
            uploadBtn!!.setOnClickListener { // Here we are initialising the progress dialog box
                dialog!!.setMessage("Uploading...")
                dialog!!.show()
                uploadPdfFiles(data!!.data)
            }
        }
    }

    private fun uploadPdfFiles(data: Uri?) {
        storageReference = FirebaseStorage.getInstance().getReference("uploads/")
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")
        val reference = storageReference!!.child("pdf_" + System.currentTimeMillis() + ".pdf")
        reference.putFile(data!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val uri = uriTask.result
            val model = UploadPdfModel(editText!!.text.toString(), uri.toString())
            databaseReference!!.child(databaseReference!!.push().key!!).setValue(model)
            dialog!!.dismiss()
        }.addOnFailureListener { e ->
            Toast.makeText(this@MainActivity3, e.message, Toast.LENGTH_SHORT).show()
        }
    }



}




