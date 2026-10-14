package com.example.blogapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        val signupText=findViewById<TextView>(R.id.signupText)

        val email=findViewById<EditText>(R.id.email).toString()
        val password=findViewById<EditText>(R.id.password).toString()

        if(email.isEmpty() || password.isEmpty()){
            signupText.isEnabled=false
        }

        signupText.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)

        }




        val login=findViewById<Button>(R.id.login)

        val auth= FirebaseAuth.getInstance()








        login.setOnClickListener {

            if(email.isEmpty() && password.isEmpty()){
                Toast.makeText(this,"Please enter email and password",Toast.LENGTH_SHORT).show()
            }

           auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
               if(it.isSuccessful){
                   val intent = Intent(this, User_Details::class.java)
                   startActivity(intent)
               }
               else{
                   Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
               }
           }




        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}