package com.example.arra

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_month.*
import kotlinx.android.synthetic.main.fragment_month_summary.*

class Month : Fragment(){

    private var main_name:String=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let { main_name = it.getString("main_name").toString()}
        Log.d("month","app_name:${main_name}")

        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setDataAtFragment(MonthEmotion(), "KB_Pay")
        month_emotion.setOnClickListener {
            setDataAtFragment(MonthEmotion(), main_name.toString())
        }
        month_summary.setOnClickListener {
            setDataAtFragment(MonthSummary(), main_name.toString())
        }
        month_topic.setOnClickListener {
            setDataAtFragment(MonthTopic(), main_name.toString())
        }
    }

    private fun setDataAtFragment(fragment: Fragment, main_name:String){
        val bundle = Bundle()
        bundle.putString("month_name", main_name)

        fragment.arguments = bundle
        childFragmentManager.beginTransaction().replace(R.id.monthFrameLayout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }


}