
package com.example.blogapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val blogContainer =
            view.findViewById<LinearLayout>(R.id.blogContainer)

        val db = FirebaseFirestore.getInstance()

        db.collection("blogs")
            .get()
            .addOnSuccessListener { result ->

                blogContainer.removeAllViews()

                for (document in result) {

                    val title = document.getString("title") ?: ""
                    val author = document.getString("authorName") ?: ""
                    val content = document.getString("content") ?: ""

                    val card = CardView(requireContext())
                    card.radius = 20f
                    card.cardElevation = 4f
                    card.setCardBackgroundColor(
                        android.graphics.Color.WHITE
                    )

                    val layout = LinearLayout(requireContext())
                    layout.orientation = LinearLayout.VERTICAL
                    layout.setPadding(24, 24, 24, 24)

                    val titleText = TextView(requireContext())
                    titleText.text = title
                    titleText.textSize = 22f

                    val authorText = TextView(requireContext())
                    authorText.text = "By $author"
                    authorText.textSize = 14f

                    val contentText = TextView(requireContext())
                    contentText.text = content
                    contentText.textSize = 16f

                    layout.addView(titleText)
                    layout.addView(authorText)
                    layout.addView(contentText)

                    card.addView(layout)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    params.setMargins(0, 0, 0, 20)

                    blogContainer.addView(card, params)
                }
            }
    }
}
