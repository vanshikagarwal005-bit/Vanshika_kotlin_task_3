
package com.example.blogapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class AddFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_add,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authorInput = view.findViewById<EditText>(R.id.authorNameInput)
        val titleInput = view.findViewById<EditText>(R.id.blogTitleInput)
        val contentInput = view.findViewById<EditText>(R.id.blogContentInput)
        val addButton = view.findViewById<Button>(R.id.addBlogButton)

        addButton.setOnClickListener {

            val author = authorInput.text.toString().trim()
            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()

            if (author.isEmpty() || title.isEmpty() || content.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all the fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            addButton.isEnabled = false

            val blog = hashMapOf(
                "authorName" to author,
                "title" to title,
                "content" to content,
                "createdAt" to FieldValue.serverTimestamp()
            )

            val uid= FirebaseAuth.getInstance()

            // when to use .add and when to use .set

            db.collection("blogs")
                .add(blog)
                .addOnSuccessListener {
                    if (!isAdded) return@addOnSuccessListener

                    Toast.makeText(
                        requireContext(),
                        "Blog added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                    authorInput.text.clear()
                    titleInput.text.clear()
                    contentInput.text.clear()

                    requireActivity()
                        .findViewById<BottomNavigationView>(R.id.bottom_nav)
                        .selectedItemId = R.id.home
                }
                .addOnFailureListener { exception ->
                    if (!isAdded) return@addOnFailureListener

                    Toast.makeText(
                        requireContext(),
                        "Failed to add blog: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnCompleteListener {
                    if (isAdded) {
                        addButton.isEnabled = true
                    }
                }
        }
    }
}
