package com.example.finance

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    var isWhite : Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        val titleHello : TextView = view.findViewById(R.id.helloTitle)
        val frameLayout : FrameLayout = view.findViewById(R.id.homeFrame)
        if(titleHello.currentTextColor == resources.getColor(R.color.white)){
            frameLayout.background = resources.getDrawable(R.drawable.darkwall)
            isWhite = false
        }
        else{
            frameLayout.background = resources.getDrawable(R.drawable.big)
            isWhite = true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val scrollView  = getView()?.findViewById<ScrollView>(R.id.homeScroll)
        val param = scrollView?.layoutParams as ViewGroup.MarginLayoutParams
        val layoutParams = scrollView?.layoutParams as? ViewGroup.MarginLayoutParams
        val resources: Resources = this.resources
        val resourceId: Int = getResources().getIdentifier("navigation_bar_height", "dimen", "android")
        val screenDensity = getResources().displayMetrics.density
        if (resourceId > 0) {
            val navHeight = resources.getDimensionPixelSize(resourceId)
            val finalNavHeight = navHeight + (64 * screenDensity )
            layoutParams?.setMargins(0 ,0, 0 , finalNavHeight.toInt())
        }

        val barChart = getView()?.findViewById<BarChart>(R.id.homeChart)
        val barEntriesList = ArrayList<BarEntry>()

        for( i in 0..sevenDays().size-1)
        {
            barEntriesList.add(BarEntry((i+1).toFloat(),sevenDays()[i]!!.toFloat()))
            println(sevenDays()[i])
        }


        val barDataSet = BarDataSet(barEntriesList, "Prenotazioni")

        val barData = BarData(barDataSet)
        barChart?.data = barData
        barChart?.animateY(1000, Easing.EaseInOutQuad)
        barDataSet.setColor(resources.getColor(R.color.blue2))
        barChart?.legend?.isEnabled = false
        barChart?.description?.isEnabled = false
        if(!isWhite){
            barChart?.xAxis?.textColor = resources.getColor(R.color.white)
            barChart?.axisLeft?.textColor = resources.getColor(R.color.white)
            barChart?.axisRight?.textColor = resources.getColor(R.color.white)
            barDataSet.setValueTextColor(Color.WHITE)
        }

        todayArrivals()

    }

    fun todayArrivals(){
        val dateAndTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val formatted = dateAndTime.format(formatter)
        val date = formatted.substringBefore(" ")
        val numGuest : TextView? =view?.findViewById(R.id.numGuest)
        val houseName : TextView? = view?.findViewById(R.id.houseName)
        val guestName : TextView? = view?.findViewById(R.id.guestName)
        val numGuest2 : TextView? =view?.findViewById(R.id.numGuest2)
        val houseName2 : TextView? = view?.findViewById(R.id.houseName2)
        val guestName2 : TextView? = view?.findViewById(R.id.guestName2)
        val todayArrivals : ConstraintLayout? = view?.findViewById(R.id.todayArrivals)
        val todayArrivals2 : ConstraintLayout? = view?.findViewById(R.id.todayArrivals2)
        val clearLayout : ConstraintLayout? = view?.findViewById(R.id.homeClearLayout)

        var loop = 0
        for( i in getItemsList())
        {
            if(i.arrDate == date)
            {

                if(loop==0){
                    numGuest?.text = i.numberOsp
                    houseName?.text = i.house
                    guestName?.text = i.names
                    loop++
                }
                else{
                    numGuest2?.text = i.numberOsp
                    houseName2?.text = i.house
                    guestName2?.text = i.names
                    loop++
                }
            }
        }
        todayArrivals?.isVisible = true
        todayArrivals2?.isVisible = true
        if(loop==0){
            todayArrivals?.isVisible = false
            todayArrivals2?.isVisible = false
        }
        if(loop==1){
            todayArrivals2?.isVisible = false
            loop=0
        }
        clearLayout?.isVisible = todayArrivals?.isVisible == false && todayArrivals2?.isVisible == false

    }
    fun sevenDays() : IntArray {
        val dateAndTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val formatted = dateAndTime.format(formatter)
        val todayDate = formatted.substringBefore(" ")
        val newDate : LocalDateTime = dateAndTime.minusDays(6)
        val lastDateTime = newDate.format(formatter)
        val lastDate = lastDateTime.substringBefore(" ")
        val sdf = SimpleDateFormat("d/M/yyyy")
        val weekChart = IntArray(7){0}
        val todayDay: Date = sdf.parse(todayDate)
        val lastDay: Date = sdf.parse(lastDate)
        for( i in getItemsList())
        {
            val arrivalDate : Date = sdf.parse(i.arrDate)
            val partenzaDate : Date = sdf.parse(i.parDate)

            if(lastDay.compareTo(arrivalDate) <=0 && todayDay.compareTo(partenzaDate)>=0)
            {
                var daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                if(daysStayed == 0L){
                    daysStayed = 1L;
                }
                val pricePerNight = i.price.toLong() / daysStayed
                val startIndex = milisecondsToDays(todayDay.time - partenzaDate.time)
                val endIndex = startIndex + daysStayed
                for( j in 0 .. weekChart.size-1){
                    if( j > startIndex&& j<= endIndex)
                    {
                        weekChart[j.toInt()]+=pricePerNight.toInt()
                    }

                }
            }
            if(arrivalDate.compareTo(lastDay) <0 && todayDay.compareTo(partenzaDate) >0 )
            {
                var daysStayed : Long = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                if(daysStayed == 0L){
                    daysStayed = 1L;
                }
                val pricePerNight = i.price.toLong() / daysStayed
                val startIndex = milisecondsToDays(todayDay.time - partenzaDate.time)
                val endIndex = 6
                for( j in 0 .. weekChart.size-1){
                    if( j > startIndex&& j<= endIndex)
                    {
                        weekChart[j.toInt()]+=pricePerNight.toInt()
                    }
                }
            }
            if(arrivalDate.compareTo(lastDay) >0 && todayDay.compareTo(partenzaDate) <0 )
            {
                var daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                if(daysStayed == 0L){
                    daysStayed = 1L;
                }
                val pricePerNight = i.price.toLong() / daysStayed
                val startIndex = 0
                val endIndex = milisecondsToDays(todayDay.time - arrivalDate.time)
                for( j in 0 .. weekChart.size-1){
                    if( j >= startIndex&& j<= endIndex)
                    {
                        weekChart[j.toInt()]+=pricePerNight.toInt()
                    }
                }
            }
            if(arrivalDate.compareTo(lastDay) <0 && todayDay.compareTo(partenzaDate) <0 )
            {
                var daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                if(daysStayed == 0L){
                    daysStayed = 1L;
                }
                val pricePerNight = i.price.toLong() / daysStayed
                val startIndex = 0
                val endIndex = 6
                for( j in 0 .. weekChart.size-1){
                    if( j >= startIndex&& j<= endIndex)
                    {
                        weekChart[j.toInt()]+=pricePerNight.toInt()
                    }
                }
            }
        }
        for( i in 0..weekChart.size-1)
        {
            if( weekChart[i]==null)
            {
                weekChart[i]=0
            }
        }

        return weekChart.reversedArray()
    }
    fun milisecondsToDays( initial: Long) : Long{
        val seconds = initial / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return days
    }
    fun getItemsList():  ArrayList<OspModelClass>{
        val databaseHandler : DatabaseHandler = DatabaseHandler(requireActivity())
        val osp : ArrayList<OspModelClass> =databaseHandler.viewOspiti()
        return osp
    }
}