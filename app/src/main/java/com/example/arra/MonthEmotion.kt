package com.example.arra

import android.content.ContentValues.TAG
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_month_emotion.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

data class Score(
    val name:String,
    val score: Int
)
class MonthEmotion : Fragment(){
    private var month_name: String = ""
    private var scoreList1 = ArrayList<Score>()
    private var scoreList2 = ArrayList<Score>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        arguments?.let { month_name = it.getString("month_name").toString()}

        return inflater.inflate(R.layout.fragment_month_emotion, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLineChart()

        val database = Firebase.database
        val myRef = database.getReference(month_name)

        myRef.child("emotion").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.child("month")
                var cnt = 0;
                for(m in test.children) {
                    for (v in m.children) {
                        if(cnt==0){
                            var value = v.value.toString()
                            scoreList1.add(Score(m.key.toString(), value.toInt()))
                        }
                        if(cnt==1){
                            var value = v.value.toString()
                            scoreList2.add(Score(m.key.toString(), value.toInt()))
                        }
                        cnt=cnt+1
                    }
                    cnt = 0
                }
                Log.d("list1",scoreList1.toString())
                Log.d("list1",scoreList2.toString())

                val entries1:ArrayList<Entry> = ArrayList()
                val entries2:ArrayList<Entry> = ArrayList()


                for(i in scoreList1.indices){
                    val score = scoreList1[i]
                    entries1.add(Entry(i.toFloat(),score.score.toFloat()))
                }
                for(i in scoreList2.indices){
                    val score = scoreList2[i]
                    entries2.add(Entry(i.toFloat(),score.score.toFloat()))
                }

                val chartData = LineData();
                val lineDataSet1 = LineDataSet(entries1,"negative")
                lineDataSet1.setColor(Color.parseColor("#c62828"))
                lineDataSet1.setCircleColor(Color.parseColor("#c62828"))
                lineDataSet1.setLineWidth(2f)
                chartData.addDataSet(lineDataSet1)

                val lineDataSet2 = LineDataSet(entries2,"positive")
                lineDataSet2.setColor(Color.parseColor("#5D6DBE"))
                lineDataSet2.setCircleColor(Color.parseColor("#5D6DBE"))
                lineDataSet2.setLineWidth(2f)
                chartData.addDataSet(lineDataSet2)
                month_chart.data = chartData
                month_chart.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fail","실패");
            }
        })


    }
    private fun initLineChart(){
        month_chart.axisLeft.setDrawGridLines(false)
        val xAxis:XAxis = month_chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        month_chart.axisRight.isEnabled = false
        month_chart.legend.isEnabled = true
        month_chart.description.isEnabled = false

        month_chart.animateX(1000, Easing.EaseInSine)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }
    inner class MyAxisFormatter : IndexAxisValueFormatter(){
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if(index<scoreList1.size){
                scoreList1[index].name
            }else{
                ""
            }
        }
    }

}
