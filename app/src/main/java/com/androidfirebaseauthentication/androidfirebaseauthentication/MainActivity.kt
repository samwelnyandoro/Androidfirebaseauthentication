package com.androidfirebaseauthentication.androidfirebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class MainActivity : AppCompatActivity() {
    private lateinit var firstname: TextView
    private lateinit var lastname: TextView
    private lateinit var email: TextView
    private lateinit var logout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        firstname = findViewById(R.id.tvFistName)
        lastname = findViewById(R.id.tvLastName)
        email = findViewById(R.id.tvEmail)
        logout = findViewById(R.id.btnLogOut)
        logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@MainActivity, LogIn::class.java)
            startActivity(intent)
            finish()
        }
        isLogin()
    }
    private fun isLogin(){
        val intent = Intent(this@MainActivity, LogIn::class.java)
        auth.currentUser?.uid?.let { loadData(it)  } ?: startActivity(intent)
    }
    private fun loadData(userId: String){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    var user: User? = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        firstname.text = user.firstname
                    }
                    if (user != null) {
                        lastname.text = user.lastname
                    }
                    if (user != null){
                        email.text = user.email
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                //
            }
        }
        database.reference.child("user")
            .child(userId).addListenerForSingleValueEvent(dataListener)
    }
}