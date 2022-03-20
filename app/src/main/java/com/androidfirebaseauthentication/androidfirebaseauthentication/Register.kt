package com.androidfirebaseauthentication.androidfirebaseauthentication
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class Register : AppCompatActivity() {
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference
        firstname = findViewById(R.id.etFirstName)
        lastname = findViewById(R.id.etLastName)
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etRegisterPassword)
        registerButton = findViewById(R.id.registerActionButton)
        registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).
            addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful){
                    val userId = auth.currentUser?.uid
                    val registerRef = dbRef.child("user").child(userId.toString())
                    val user = User(firstname.text.toString(), lastname.text.toString())
                    registerRef.setValue(user).addOnSuccessListener {
                        val intent = Intent(this@Register, LogIn::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}