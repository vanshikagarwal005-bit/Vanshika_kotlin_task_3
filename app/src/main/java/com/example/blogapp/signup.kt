package com.example.blogapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blogapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class signup : AppCompatActivity() {
     private lateinit var binding: ActivitySignupBinding
     private val auth = FirebaseAuth.getInstance()
     private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


       binding.signup.setOnClickListener {
           val name =binding.fullName.text.toString().trim()
           val email = binding.email.text.toString().trim()
           val password = binding.password.text.toString().trim()

           if(name.isEmpty() || email.isEmpty() || password.length<6){
               Toast.makeText(this,"Invalid Credentials (min  password length is 6)", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }

           if(!binding.cbTerms.isChecked){
               Toast.makeText(this,"Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }

           auth.createUserWithEmailAndPassword(email, password)
               .addOnSuccessListener { result ->
                   val uid = result.user!!.uid
                   val user = hashMapOf("name" to name, "email" to email)

                   db.collection("users").document(uid).set(user)
                       .addOnSuccessListener {
                           startActivity(Intent(this, UserDetailsActivity::class.java))
                           finish()
                       }
                       .addOnFailureListener { e ->
                           Log.e("Signup", "Firestore save failed", e)
                           Toast.makeText(this, "Firestore: ${e.message}", Toast.LENGTH_LONG).show()
                       }
               }
               .addOnFailureListener { e ->
                   Log.e("Signup", "Auth failed", e)
                   Toast.makeText(this, "Auth: ${e.message}", Toast.LENGTH_LONG).show()
               }

       }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}












