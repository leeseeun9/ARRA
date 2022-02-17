package com.example.arra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            search_text.text.toString()
            intent.putExtra("application", search_text.text.toString())
            startActivity(intent)
        }
    }
}