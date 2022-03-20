package com.androidfirebaseauthentication.androidfirebaseauthentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerbuttton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        registerbuttton = findViewById(R.id.registerButton)

        registerbuttton.setOnClickListener{
            val intent = Intent(this@LogIn, Register::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{
            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).
            addOnCompleteListener {
                val intentToMain = Intent(this@LogIn, MainActivity::class.java)
                startActivity(intentToMain)
            }

        }
    }
}