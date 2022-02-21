package com.example.arra

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import android.widget.ListView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_month_emotion.*
import kotlinx.android.synthetic.main.fragment_month_summary.*
import java.util.HashMap
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_month.*
import com.google.firebase.database.DataSnapshot as DataSnapshot

class MonthSummary : Fragment(){
    private var month_name: String = ""
    var check = false
    var date = "2022-01"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let { month_name = it.getString("month_name").toString()}

        return inflater.inflate(R.layout.fragment_month_summary, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        month_summary_spinner.adapter =
            activity?.let { ArrayAdapter.createFromResource(it, R.array.itemList, android.R.layout.simple_spinner_item) }

        month_summary_spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                date = month_summary_spinner.adapter.getItem(position).toString()

                val database = Firebase.database
                val myRef = database.getReference(month_name)

                Log.d("date is ", date);
                myRef.child("summarize/month").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val test = snapshot.child(date)
                        var str = ""
                        for(m in test.children){
                            Log.d("Tag: value is ", m.value.toString())
                            str += m.value.toString()+"\n"+"\n"
                        }
                        summary_text1.text = str
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("fail","실패");
                    }
                })
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}

