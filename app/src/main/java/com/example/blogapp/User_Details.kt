package com.example.blogapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.blogapp.R
import com.example.blogapp.databinding.ActivityUserDetailsBinding
import kotlin.jvm.java

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get().addOnSuccessListener {
            binding.fullName.setText(it.getString("name"))
        }

        binding.female.setOnClickListener { selectGender("Female") }
        binding.Male.setOnClickListener { selectGender("Male") }
        binding.others.setOnClickListener { selectGender("Others") }

        binding.saveNcont.setOnClickListener {
            val name = binding.fullName.text.toString().trim()
            val age = binding.age.text.toString().trim()
            val city = binding.city.text.toString().trim()

            if (name.isEmpty() || gender.isEmpty() || age.isEmpty() || city.isEmpty()) {
                Toast.makeText(this, "Please fill everything", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = mapOf(
                "name" to name,
                "gender" to gender,
                "age" to age.toInt(),
                "city" to city
            )
            db.collection("users").document(uid).set(data, SetOptions.merge())
                .addOnSuccessListener {
                    startActivity(Intent(this, homescreen::class.java))
                    finish()
                }
                .addOnFailureListener { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
        }
    }

    private fun selectGender(g: String) {
        gender = g
        val selected = ContextCompat.getColor(this, R.color.light_purple)
        val normal = ContextCompat.getColor(this, R.color.background)
        binding.female.setCardBackgroundColor(if (g == "Female") selected else normal)
        binding.Male.setCardBackgroundColor(if (g == "Male") selected else normal)
        binding.others.setCardBackgroundColor(if (g == "Others") selected else normal)
    }
}