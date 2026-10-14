package com.example.blogapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_Details : AppCompatActivity() {

    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_details)


        val saveNcont=findViewById<Button>(R.id.saveNcont)
        saveNcont.setOnClickListener {
            val intent = Intent(this, homescreen::class.java)
            startActivity(intent)

        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val femaleCard = findViewById<CardView>(R.id.female)
        val maleCard = findViewById<CardView>(R.id.Male)
        val femaleText = findViewById<TextView>(R.id.femaleText)
        val maleText = findViewById<TextView>(R.id.maleText)

        femaleCard.setOnClickListener {
            selectedGender = "Female"
            setSelected(femaleCard, femaleText, maleCard, maleText)
        }

        maleCard.setOnClickListener {
            selectedGender = "Male"
            setSelected(maleCard, maleText, femaleCard, femaleText)
        }
    }

    private fun setSelected(
        selectedCard: CardView, selectedText: TextView,
        otherCard: CardView, otherText: TextView
    ) {
        selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
        selectedText.setTextColor(ContextCompat.getColor(this, R.color.white))

        otherCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.background))
        otherText.setTextColor(ContextCompat.getColor(this, R.color.black))

    }
}