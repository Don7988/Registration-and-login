package com.example.recylerview

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recylerview.databinding.ActivityRegistrationPageBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration_page.*

class RegistrationPage : AppCompatActivity() {


    private var backpressed : Long=0
    private lateinit var binding: ActivityRegistrationPageBinding
    lateinit var emailIdForUser :String
    private var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//validating registration and logging in
        binding.registerButton.setOnClickListener{
            if (!sum()){
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email_register.text.toString(),password_register.text.toString()).addOnCompleteListener{task->
                    if (task.isSuccessful){
                        username_register.setText("")
                        password_register.setText("")
                        email_register.setText("")
                        con_password_register.setText("")
                        startActivity(Intent(this,MainActivity::class.java))
                        Toast.makeText(this@RegistrationPage,"success",Toast.LENGTH_LONG).show()
                    }
                }
                Toast.makeText(applicationContext,"Success",Toast.LENGTH_LONG).show()
            }
        }
    }
//checking the parameters of input data is correct
    private fun sum() :Boolean{
        var abc:Boolean
        abc=true
        if (username_register.length()==0){
            username_register.error = "Empty"
            abc= false
        }

        if(binding.passwordRegister.text.toString() != binding.conPasswordRegister.text.toString()){
            password_register.error = "Mismatch"
            con_password_register.error = "Mismatch"
            abc= false

        }
        if(password_register.text.toString().isEmpty()){
            password_register.error = "Empty"
            abc= false
        }
        if (password_register.text.toString().length<8){
            password_register.error = "Min 8 char"
            abc= false
        }
        if (con_password_register.text.toString().isEmpty()) {
            con_password_register.error = "Empty"
            abc= false
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email_register.text).matches()) {
             emailIdForUser = email_register.text.toString()
            abc= true
        }

        else if(email_register.length()==0){
            email_register.error = "Empty"
            abc= false
        }
        else{
            email_register.error = "eg:abc@gmail.com"
            abc= false
        }

        return abc
    }
    private fun userDetails(userName:String,userEmail:String,userPassword:String,userConPassword:String){
    }
    override fun onBackPressed() {

        finish()
    }
}
