package com.example.arra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val firebaseDatabase = FirebaseDatabase.getInstance() // 실시간 데이터 db
        val firestoredb = FirebaseFirestore.getInstance() // firestore db

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        search_page.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        var temp = intent.getStringExtra("application").toString()
        val regex = Regex("[^A-Za-z0-9]")
        var result = regex.replace(temp, "")
        var app_name = result.toLowerCase()

        firestoredb.collection("app_name").document(app_name).get()
            .addOnSuccessListener { result ->
                app_id.text = result["korean"].toString()
                Glide.with(this)
                    .load(result["image_url"])
                    .into(app_logo)
            }

        //supportFragmentManager.beginTransaction().replace(R.id.frameLayout, Month()).commit()
        setDataAtFragment(Month(),app_name)

        monthBtn.setOnClickListener {
            setDataAtFragment(Month(), app_name)

        }
        updateBtn.setOnClickListener {
            setDataAtFragment(Update(), app_name)
        }


    }
    private fun setDataAtFragment(fragment: Fragment, app_name:String){
        val bundle = Bundle()
        bundle.putString("main_name", app_name)

        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

}