package com.example.arra

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_month_emotion.*
import kotlinx.android.synthetic.main.fragment_month_summary.*
import kotlinx.android.synthetic.main.fragment_month_topic.*

data class Topic(
    val name:String,
    val topic: Double
)
class MonthTopic : Fragment(){
    private var month_name: String = ""
    private var topicList1 = ArrayList<Topic>()
    private var topicList2 = ArrayList<Topic>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let { month_name = it.getString("month_name").toString()}

        return inflater.inflate(R.layout.fragment_month_topic, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        num.minValue = 0
        num.maxValue = 7
        var date = ""
        var topic_num = 0

        val database = Firebase.database
        val myRef = database.getReference(month_name)

        initBarchart()
        myRef.child("lda/2021-2/긍정").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.child("0")
                for(m in test.children) {
                    var value = m.value.toString()
                    topicList1.add(Topic(m.key.toString(), value.toDouble()))
                }
                Log.d("list1",topicList1.toString())

                val entries1:ArrayList<BarEntry> = ArrayList()


                for(i in topicList1.indices){
                    val topic = topicList1[i]
                    entries1.add(BarEntry(i.toFloat(),topic.topic.toFloat()*1000))
                }

                val barDataSet  = BarDataSet(entries1,"positive")
                barDataSet.setColor(Color.parseColor("#5D6DBE"))
                val data = BarData(barDataSet)
                positive_bar.data = data
                positive_bar.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fail","실패");
            }
        })
        myRef.child("lda/2021-2/부정").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.child("0")
                var cnt = 0;
                for (m in test.children) {
                    var value = m.value.toString()
                    topicList2.add(Topic(m.key.toString(), value.toDouble()))
                }
                Log.d("list2", topicList2.toString())

                val entries2: ArrayList<BarEntry> = ArrayList()


                for (i in topicList2.indices) {
                    val topic = topicList2[i]
                    entries2.add(BarEntry(i.toFloat(), topic.topic.toFloat() * 1000))
                }

                val barDataSet = BarDataSet(entries2, "negative")
                barDataSet.setColor(Color.parseColor("#c62828"))
                val data = BarData(barDataSet)
                negative_bar.data = data
                negative_bar.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fail", "실패");
            }
       })

        topic_spinner.adapter =
            activity?.let { ArrayAdapter.createFromResource(it, R.array.topic_year, android.R.layout.simple_spinner_item) }
        topic_spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                date = topic_spinner.adapter.getItem(position).toString()


                num.setOnValueChangedListener { picker, oldVal, newVal ->

                    var topicList1 = ArrayList<Topic>()
                    var topicList2 = ArrayList<Topic>()

                    class MAxisFormatter : IndexAxisValueFormatter(){
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            val index = value.toInt()
                            return if(index<topicList1.size){
                                topicList1[index].name
                            }else{
                                ""
                            }
                        }
                    }
                    class MAxisFormatter1 : IndexAxisValueFormatter(){
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            val index = value.toInt()
                            return if(index<topicList2.size){
                                topicList2[index].name
                            }else{
                                ""
                            }
                        }
                    }


                    val description = Description()
                    positive_bar.description.isEnabled=false
                    positive_bar.axisRight.isEnabled = false
                    positive_bar.animateX(1000)
                    positive_bar.animateY(1000)

                    val xAxis1: XAxis = positive_bar.xAxis
                    xAxis1.position = XAxis.XAxisPosition.BOTTOM
                    xAxis1.granularity = 1f
                    xAxis1.textColor = Color.BLACK
                    xAxis1.setDrawAxisLine(false)
                    xAxis1.setDrawGridLines(false)
                    xAxis1.valueFormatter = MAxisFormatter()

                    val leftAxis1: YAxis = positive_bar.axisLeft
                    leftAxis1.setDrawAxisLine(false)
                    leftAxis1.textColor = Color.BLACK


                    val legend1: Legend = positive_bar.legend
                    //setting the shape of the legend form to line, default square shape
                    legend1.form = Legend.LegendForm.LINE
                    legend1.textSize = 11f
                    legend1.textColor = Color.BLACK
                    legend1.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    legend1.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    legend1.orientation = Legend.LegendOrientation.HORIZONTAL
                    legend1.setDrawInside(false)

                    negative_bar.description.isEnabled=false
                    negative_bar.axisRight.isEnabled = false
                    negative_bar.animateX(1000)
                    negative_bar.animateY(1000)

                    val xAxis: XAxis = negative_bar.xAxis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.granularity = 1f
                    xAxis.textColor = Color.BLACK
                    xAxis.setDrawAxisLine(false)
                    xAxis.setDrawGridLines(false)
                    xAxis.valueFormatter = MAxisFormatter1()

                    val leftAxis: YAxis = negative_bar.axisLeft
                    leftAxis.setDrawAxisLine(false)
                    leftAxis.textColor = Color.BLACK


                    val legend: Legend = negative_bar.legend
                    legend.form = Legend.LegendForm.LINE
                    legend.textSize = 11f
                    legend.textColor = Color.BLACK
                    legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    legend.orientation = Legend.LegendOrientation.HORIZONTAL
                    legend.setDrawInside(false)

                    Log.d("list1",date+" "+newVal)
                    myRef.child("lda/"+date+"/긍정").addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val test = snapshot.child(newVal.toString())
                            for(m in test.children) {
                                var value = m.value.toString()
                                topicList1.add(Topic(m.key.toString(), value.toDouble()))
                            }
                            Log.d("list1",topicList1.toString())

                            val entries1:ArrayList<BarEntry> = ArrayList()


                            for(i in topicList1.indices){
                                val topic = topicList1[i]
                                entries1.add(BarEntry(i.toFloat(),topic.topic.toFloat()*1000))
                            }


                            val barDataSet  = BarDataSet(entries1,"positive")
                            barDataSet.setColor(Color.parseColor("#5D6DBE"))
                            val data = BarData(barDataSet)
                            positive_bar.data = data
                            positive_bar.invalidate()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d("fail","실패");
                        }
                    })
                    myRef.child("lda/"+date+"/부정").addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val test = snapshot.child(newVal.toString())
                            var cnt = 0;
                            for (m in test.children) {
                                var value = m.value.toString()
                                topicList2.add(Topic(m.key.toString(), value.toDouble()))
                            }
                            Log.d("list2", topicList2.toString())

                            val entries2: ArrayList<BarEntry> = ArrayList()


                            for (i in topicList2.indices) {
                                val topic = topicList2[i]
                                entries2.add(BarEntry(i.toFloat(), topic.topic.toFloat() * 1000))
                            }

                            val barDataSet = BarDataSet(entries2, "negative")
                            barDataSet.setColor(Color.parseColor("#c62828"))
                            val data = BarData(barDataSet)
                            negative_bar.data = data
                            negative_bar.invalidate()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d("fail", "실패");
                        }
                    })
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }
    private fun initBarchart(){

        val description = Description()
        positive_bar.description.isEnabled=false
        positive_bar.axisRight.isEnabled = false
        positive_bar.animateX(1000)
        positive_bar.animateY(1000)

        val xAxis1: XAxis = positive_bar.xAxis
        xAxis1.position = XAxis.XAxisPosition.BOTTOM
        xAxis1.granularity = 1f
        xAxis1.textColor = Color.BLACK
        xAxis1.setDrawAxisLine(false)
        xAxis1.setDrawGridLines(false)
        xAxis1.valueFormatter = MyAxisFormatter()

        val leftAxis1: YAxis = positive_bar.axisLeft
        leftAxis1.setDrawAxisLine(false)
        leftAxis1.textColor = Color.BLACK
//
//        val rightAxis1: YAxis = positive_bar.axisLeft
//        rightAxis1.setDrawAxisLine(false)
//        rightAxis1.textColor = Color.BLUE

        val legend1: Legend = positive_bar.legend
        //setting the shape of the legend form to line, default square shape
        legend1.form = Legend.LegendForm.LINE
        legend1.textSize = 11f
        legend1.textColor = Color.BLACK
        legend1.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend1.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend1.orientation = Legend.LegendOrientation.HORIZONTAL
        legend1.setDrawInside(false)

        negative_bar.description.isEnabled=false
        negative_bar.axisRight.isEnabled = false
        negative_bar.animateX(1000)
        negative_bar.animateY(1000)

        val xAxis: XAxis = negative_bar.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.textColor = Color.BLACK
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = MyAxisFormatter1()

        val leftAxis: YAxis = negative_bar.axisLeft
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = Color.BLACK


        val legend: Legend = negative_bar.legend
        legend.form = Legend.LegendForm.LINE
        legend.textSize = 11f
        legend.textColor = Color.BLACK
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
    }
    inner class MyAxisFormatter : IndexAxisValueFormatter(){
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if(index<topicList1.size){
                topicList1[index].name
            }else{
                ""
            }
        }
    }
    inner class MyAxisFormatter1 : IndexAxisValueFormatter(){
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if(index<topicList2.size){
                topicList2[index].name
            }else{
                ""
            }
        }
    }
}