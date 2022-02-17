package com.example.arra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var app_name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var temp = intent.getStringExtra("application").toString()
        val regex = Regex("[^A-Za-z0-9]")
        var result = regex.replace(temp, "")
        app_name = result.toLowerCase()

        val firebaseDatabase = FirebaseDatabase.getInstance() // 실시간 데이터 db
        val firestoredb = FirebaseFirestore.getInstance() // firestore db

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayShowCustomEnabled(true)




        firestoredb.collection("app_name").document(app_name).get()
            .addOnSuccessListener { result ->
                app_id.text = result["korean"].toString()
                Glide.with(this)
                    .load(result["image_url"])
                    .into(app_logo)
            }

        //supportFragmentManager.beginTransaction().replace(R.id.frameLayout, Month()).commit()


        bnv.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.analysis->{
                        setDataAtFragment(Month(),app_name)
                    }
                    R.id.tag->{
                        setDataAtFragment(Update(),app_name)
                    }
                }
                true
            }
            selectedItemId = R.id.analysis
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_search->{
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setDataAtFragment(fragment: Fragment, app_name:String){
        val bundle = Bundle()
        bundle.putString("main_name", app_name)

        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

}