package com.example.recylerview

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_uploadfile.*

class uploadfile : AppCompatActivity() {
   private lateinit var filepath: Uri
   private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploadfile)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().getReference("mydocuments")



        filelogo.visibility = View.INVISIBLE
        cancelfile.visibility = View.INVISIBLE

        cancelfile.setOnClickListener {
            filelogo.visibility = View.INVISIBLE
            cancelfile.visibility = View.INVISIBLE
            imagebrowse.visibility = View.VISIBLE
        }


        imagebrowse.setOnClickListener {
            Dexter.withContext(applicationContext)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        val intent = Intent()
                        intent.type = "application/pdf"
                        intent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(
                            Intent.createChooser(intent, "Select Pdf Files"),
                            101
                        )
                    }

                    override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                    override fun onPermissionRationaleShouldBeShown(
                        permissionRequest: PermissionRequest,
                        permissionToken: PermissionToken
                    ) {
                        permissionToken.continuePermissionRequest()
                    }
                }).check()
        }

        imageupload.setOnClickListener { processupload(filepath) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            filepath = data!!.data!!
            filelogo.visibility = View.VISIBLE
            cancelfile.visibility = View.VISIBLE
            imagebrowse.visibility = View.INVISIBLE
        }
    }


    fun processupload(filepath: Uri?) {
        val pd = ProgressDialog(this)
        pd.setTitle("File Uploading....!!!")
        pd.show()
        val reference = storageReference!!.child("uploads/" + System.currentTimeMillis() + ".pdf")
        reference.putFile(filepath!!)
            .addOnSuccessListener {
                reference.downloadUrl.addOnSuccessListener { uri ->
                    val obj = mymodel(filetitle.text.toString(), uri.toString(), 0, 0, 0)
                    databaseReference!!.child(databaseReference!!.push().key!!).setValue(obj)
                    pd.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_LONG).show()
                    filelogo.visibility = View.INVISIBLE
                    cancelfile.visibility = View.INVISIBLE
                    imagebrowse.visibility = View.VISIBLE
                    filetitle.setText(" ")
                }
            }
            .addOnProgressListener { taskSnapshot ->
                val percent =
                    (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
                pd.setMessage("Uploaded :" + percent.toInt() + "%")
            }
    }
}
