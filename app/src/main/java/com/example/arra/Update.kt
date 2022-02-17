package com.example.arra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_month.*
import kotlinx.android.synthetic.main.fragment_update.*

class Update : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emotion1()
        update_emotion.setOnClickListener {
            emotion1()
        }
        update_summary.setOnClickListener {
            summary1()
        }
        update_topic.setOnClickListener {
            topic1()
        }
    }


    private fun emotion1(){
        childFragmentManager.beginTransaction().replace(R.id.updateFrameLayout, UpdateEmotion()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    private fun summary1(){
        childFragmentManager.beginTransaction().replace(R.id.updateFrameLayout, UpdateSummary()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }
    private fun topic1(){
        childFragmentManager.beginTransaction().replace(R.id.updateFrameLayout, UpdateTopic()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }
}