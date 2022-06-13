package com.example.recylerview

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recylerview.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity(){
    private lateinit var sharedPreferences: SharedPreferences
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var backpresse: Long =0
    private lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.loginBtn.setOnClickListener {
            if (!sum()) {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
            } else {
                authenticationForUser()
            }
        }
        binding.regLoginBtn.setOnClickListener {
            creatingAccount()
        }
        binding.forgotPassword.setOnClickListener{
            forgotPasswordByUser()
        }
    }

    private fun authenticationForUser() {
        auth.signInWithEmailAndPassword(
            username_login.text.toString(),
            password_login.text.toString()
        ).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                username_login.setText("")
                password_login.setText("")
                startActivity(Intent(this@LoginPage, MainActivity::class.java))
                finish()
            }

            else{
                Toast.makeText(this,"wrong Id or password",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sum(): Boolean {
        var abc: Boolean
        abc = true
        if (username_login.length() == 0) {
            username_login.error = "Empty"
            abc = false
        }
        if (password_login.length() == 0) {
            password_login.error = "Empty"
            abc = false
        }
        return abc
    }
    private fun creatingAccount(){
        startActivity(Intent(this@LoginPage, RegistrationPage::class.java))
    }

    override fun onBackPressed() {
        backPressed()
    }
    private fun backPressed() {
        
        if (backpresse + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }
        else {
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_LONG).show()
            backpresse = System.currentTimeMillis()
        }
    }
    private fun forgotPasswordByUser(){
        intent=Intent(this@LoginPage, ForgotPassword::class.java)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser?.let {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }


}