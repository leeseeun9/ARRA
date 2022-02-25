package com.example.arra

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_update.*

class Update : Fragment(){
    private var main_name:String=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let { main_name = it.getString("main_name").toString()}
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val database = Firebase.database
        val myRef = database.getReference(main_name)

        myRef.child("tag").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.child("1")
                var str1 = ""
                var str2 = ""
                var str3 = ""
                var cnt = 0

                for(m in test.children){
                    if(cnt==0){
                        positive_tag1.text = "#"+m.key.toString()
                        str1 = m.value.toString()
                        str1 = str1.replace("[","")
                        str1 = str1.replace("]","")
                    }
                    else if(cnt==1){
                        positive_tag2.text = "#"+m.key.toString()
                        str2 = m.value.toString()
                        str2 = str2.replace("[","")
                        str2 = str2.replace("]","")
                    }
                    else{
                        positive_tag3.text = "#"+m.key.toString()
                        str3 = m.value.toString()
                        str3 = str3.replace("[","")
                        str3 = str3.replace("]","")
                    }
                    cnt=cnt+1
                }
                positive_tag1.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
                    when (motionEvent.action){
                        MotionEvent.ACTION_DOWN ->{
                            tag_text.text = str1
                        }
                    }
                    return@OnTouchListener true
                })
                positive_tag2.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
                    when (motionEvent.action){
                        MotionEvent.ACTION_DOWN ->{
                            tag_text.text = str2
                        }
                    }
                    return@OnTouchListener true
                })
                positive_tag3.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
                    when (motionEvent.action){
                        MotionEvent.ACTION_DOWN ->{
                            tag_text.text = str3
                        }
                    }
                    return@OnTouchListener true
                })

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fail","실패");
            }
        })

        myRef.child("tag").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.child("0")
                var str1 = ""
                var str2 = ""
                var str3 = ""
                var cnt = 0

                for(m in test.children){
                    if(cnt==0){
                        negative_tag1.text = "#"+m.key.toString()
                        str1 = m.value.toString()
                        str1 = str1.replace("[","")
                        str1 = str1.replace("]","")
                    }
                    else if(cnt==1){
                        negative_tag2.text = "#"+m.key.toString()
                        str2 = m.value.toString()
                        str2 = str2.replace("[","")
                        str2 = str2.replace("]","")
                    }
                    else{
                        negative_tag3.text = "#"+m.key.toString()
                        str3 = m.value.toString()
                        str3 = str3.replace("[","")
                        str3 = str3.replace("]","")
                    }
                    cnt=cnt+1
                }
                negative_tag1.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
                    when (motionEvent.action){
                        MotionEvent.ACTION_DOWN ->{
                            tag_text.text = str1
                        }
                    }
                    return@OnTouchListener true
                })
                negative_tag2.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
                    when (motionEvent.action){
                        MotionEvent.ACTION_DOWN ->{
                            tag_text.text = str2
                        }
                    }
                    return@OnTouchListener true
                })
                negative_tag3.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
                    when (motionEvent.action){
                        MotionEvent.ACTION_DOWN ->{
                            tag_text.text = str3
                        }
                    }
                    return@OnTouchListener true
                })

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fail","실패");
            }
        })
    }
}