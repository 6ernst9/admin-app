package com.example.finance

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class FinanceFragment : Fragment() {
    var isWhite : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_finance, container, false)
        val titleFinance : TextView = view.findViewById(R.id.financeTitle)
        val frameLayout : FrameLayout = view.findViewById(R.id.financeFrame)
        if(titleFinance.currentTextColor == resources.getColor(R.color.white)){
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
        val scrollView  = getView()?.findViewById<ScrollView>(R.id.scrollView)
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


        val barChart = getView()?.findViewById<BarChart>(R.id.weekChart)
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

        val pieChart = getView()?.findViewById<PieChart>(R.id.pieChart)
        val pieEntriesList = ArrayList<PieEntry>()
        pieEntriesList.add(PieEntry(methodPercentage().get(0).toFloat()))
        pieEntriesList.add(PieEntry(methodPercentage().get(1).toFloat()))
        pieEntriesList.add(PieEntry(methodPercentage().get(2).toFloat()))

        val pieDataSet = PieDataSet(pieEntriesList, "Prenotazioni")
        val pieData = PieData(pieDataSet)
        pieChart?.data = pieData
        pieChart?.setHoleColor(resources.getColor(R.color.transparent))
        pieChart?.animateY(1000, Easing.EaseInOutQuad)
        pieChart?.transparentCircleRadius
        val colors: ArrayList<Int> = ArrayList()
        pieChart?.legend?.isEnabled = false
        colors.add(resources.getColor(R.color.blue_theme))
        colors.add(resources.getColor(R.color.blue2))
        colors.add(resources.getColor(R.color.pink))
        pieDataSet.sliceSpace = 1f
        pieDataSet.colors = colors
        pieChart?.description?.isEnabled = false
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(15f)
        pieData.setValueTextColor(Color.WHITE)

        val prevMonth = getView()?.findViewById<ImageView>(R.id.prevMonth)
        val nextMonth = getView()?.findViewById<ImageView>(R.id.nextMonth)
        var lineDateAndTime = LocalDateTime.now()
        monthlyChart(lineDateAndTime)

        prevMonth?.setOnClickListener { view ->
            lineDateAndTime = lineDateAndTime.minusMonths(1)
            monthlyChart(lineDateAndTime)
        }
        nextMonth?.setOnClickListener { view ->
            lineDateAndTime = lineDateAndTime.plusMonths(1)
            monthlyChart(lineDateAndTime)
        }

        val prevMonthProfit = getView()?.findViewById<ImageView>(R.id.prevMonthProfit)
        val nextMonthProfit = getView()?.findViewById<ImageView>(R.id.nextMonthProfit)
        val monthTitleProfit = getView()?.findViewById<TextView>(R.id.monthlyProfitMonth)
        val monthNumberProfit = getView()?.findViewById<TextView>(R.id.monthlyProfitNumber)
        var monthlyProfitDate = LocalDateTime.now()
        var dateMonthYear = dateToMonthYear(monthlyProfitDate)
        monthTitleProfit?.text = "${carenzeDiMezi(dateToMonth(monthlyProfitDate))} ${dateToYear(monthlyProfitDate)}"
        monthNumberProfit?.text = "+${pricePerMonth(dateMonthYear)}€"
        prevMonthProfit?.setOnClickListener { view ->
            monthlyProfitDate = monthlyProfitDate.minusMonths(1)
            monthTitleProfit?.text = "${carenzeDiMezi(dateToMonth(monthlyProfitDate))} ${dateToYear(monthlyProfitDate)}"
            dateMonthYear = dateToMonthYear(monthlyProfitDate)
            monthNumberProfit?.text = "+${pricePerMonth(dateMonthYear)}€"
        }
        nextMonthProfit?.setOnClickListener { view ->
            monthlyProfitDate = monthlyProfitDate.plusMonths(1)
            monthTitleProfit?.text = "${carenzeDiMezi(dateToMonth(monthlyProfitDate))} ${dateToYear(monthlyProfitDate)}"
            dateMonthYear = dateToMonthYear(monthlyProfitDate)
            monthNumberProfit?.text = "+${pricePerMonth(dateMonthYear)}€"
        }


        val lastMonthProfit1 = getView()?.findViewById<TextView>(R.id.lastMonthProfit1)
        val lastMonthProfit2 = getView()?.findViewById<TextView>(R.id.lastMonthProfit2)
        val lastMonthProfit3 = getView()?.findViewById<TextView>(R.id.lastMonthProfit3)
        val lastMonthProfit4 = getView()?.findViewById<TextView>(R.id.lastMonthProfit4)
        val lastMonthProfit5 = getView()?.findViewById<TextView>(R.id.lastMonthProfit5)
        val lastMonthProfit6 = getView()?.findViewById<TextView>(R.id.lastMonthProfit6)

        val lastMonthTitle1 = getView()?.findViewById<TextView>(R.id.lastMonth1)
        val lastMonthTitle2 = getView()?.findViewById<TextView>(R.id.lastMonth2)
        val lastMonthTitle3 = getView()?.findViewById<TextView>(R.id.lastMonth3)
        val lastMonthTitle4 = getView()?.findViewById<TextView>(R.id.lastMonth4)
        val lastMonthTitle5 = getView()?.findViewById<TextView>(R.id.lastMonth5)
        val lastMonthTitle6 = getView()?.findViewById<TextView>(R.id.lastMonth6)

        val dateAndTime = LocalDateTime.now()
        val lastMonth1 = dateAndTime.minusMonths(1)
        val lastMonth2 = dateAndTime.minusMonths(2)
        val lastMonth3 = dateAndTime.minusMonths(3)
        val lastMonth4 = dateAndTime.minusMonths(4)
        val lastMonth5 = dateAndTime.minusMonths(5)
        val lastMonth6 = dateAndTime.minusMonths(6)

        lastMonthTitle1?.text = dateToMonth(lastMonth1)
        lastMonthTitle2?.text = dateToMonth(lastMonth2)
        lastMonthTitle3?.text = dateToMonth(lastMonth3)
        lastMonthTitle4?.text = dateToMonth(lastMonth4)
        lastMonthTitle5?.text = dateToMonth(lastMonth5)
        lastMonthTitle6?.text = dateToMonth(lastMonth6)

        lastMonthProfit1?.text = "+${profitSixMonths()[0]}€"
        lastMonthProfit2?.text = "+${profitSixMonths()[1]}€"
        lastMonthProfit3?.text = "+${profitSixMonths()[2]}€"
        lastMonthProfit4?.text = "+${profitSixMonths()[3]}€"
        lastMonthProfit5?.text = "+${profitSixMonths()[4]}€"
        lastMonthProfit6?.text = "+${profitSixMonths()[5]}€"

        val prevBtn = getView()?.findViewById<ImageView>(R.id.prevYear)
        val nextBtn = getView()?.findViewById<ImageView>(R.id.nextYear)
        val currentYear = getView()?.findViewById<TextView>(R.id.anualProfitYear)
        val anualProfit = getView()?.findViewById<TextView>(R.id.anualProfitNumber)

        var sliderDateAndTime = dateAndTime

        anualProfit?.text = "+${anuallySlider(sliderDateAndTime)[1]}€"
        currentYear?.text = anuallySlider(sliderDateAndTime)[0].toString()
        prevBtn?.setOnClickListener { view ->
            sliderDateAndTime = sliderDateAndTime.minusYears( 1)
            anualProfit?.text = "+${anuallySlider(sliderDateAndTime)[1]}€"
            currentYear?.text = anuallySlider(sliderDateAndTime)[0].toString()
        }
        nextBtn?.setOnClickListener { view ->
            sliderDateAndTime = sliderDateAndTime.plusYears(1)
            anualProfit?.text = "+${anuallySlider(sliderDateAndTime)[1]}€"
            currentYear?.text = anuallySlider(sliderDateAndTime)[0].toString()
        }

    }
    fun carenzeDiMezi( month: String) : String {
        when (month) {
            "Gennaio" -> return "Gen"
            "Febbraio" -> return "Feb"
            "Marzo" -> return "Mar"
            "Aprile" -> return "Apr"
            "Maggio" -> return "Mag"
            "Giugno" -> return "Giu"
            "Luglio" -> return "Lug"
            "Agosto" -> return "Ago"
            "Settembre" -> return "Sep"
            "Ottobre" -> return "Ott"
            "Novembre" -> return "Nov"
            "Dicembre" -> return "Dic"
            else -> {
                return "A risotto una problema"
            }
        }
    }
    fun monthlyChart ( date: LocalDateTime) {
        val lineChart = getView()?.findViewById<LineChart>(R.id.lineChart)
        val lineEntriesList = ArrayList<Entry>()
        val lineChartMonth = getView()?.findViewById<TextView>(R.id.lineChartMonth)
        lineChartMonth?.text = "${dateToMonth(date)} ${dateToYear(date)}"
        for(i in 0..monthNumOfDays(date)-1)
        {
            lineEntriesList.add(Entry((i+1).toFloat(), chartMonth(date)[i].toFloat()))
        }

        val lineDataSet = LineDataSet(lineEntriesList, "Prenotazioni")
        val lineData = LineData(lineDataSet)
        lineChart?.data = lineData
        lineChart?.legend?.isEnabled = false
        lineChart?.animateY(1000, Easing.EaseInOutQuad)
        lineDataSet.setColor(resources.getColor(R.color.blue_theme))
        lineChart?.description?.isEnabled = false
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawFilled(true)
        lineDataSet.setDrawValues(false)
        lineDataSet.setFillColor(resources.getColor(R.color.blue_theme))
        lineDataSet.setColor(resources.getColor(R.color.blue_theme))
        lineDataSet.setFillAlpha(255);
        lineDataSet.setDrawCircles(false);
        if(!isWhite){
            lineChart?.xAxis?.textColor = resources.getColor(R.color.white)
            lineChart?.axisLeft?.textColor = resources.getColor(R.color.white)
            lineChart?.axisRight?.textColor = resources.getColor(R.color.white)
            lineDataSet.setValueTextColor(Color.WHITE)
        }
    }

    fun monthNumOfDays(date: LocalDateTime): Int {
        val month = dateToMonth(date)
        var leapYear : Int = 0
        if((dateToYear(date).toInt() %4 ==0 && dateToYear(date).toInt() % 100 != 0) || (dateToYear(date).toInt()%4==0)){
            leapYear = 29
        }
        else{
            leapYear = 28
        }
        when (month) {
            "Gennaio" -> return 31
            "Febbraio" -> return leapYear
            "Marzo" -> return 31
            "Aprile" -> return 30
            "Maggio" -> return 31
            "Giugno" -> return 30
            "Luglio" -> return 31
            "Agosto" -> return 31
            "Settembre" -> return 30
            "Ottobre" -> return 31
            "Novembre" -> return 30
            "Dicembre" -> return 31
            else -> {
                return 0
            }
        }
    }

    fun anuallySlider ( date: LocalDateTime) : IntArray{
        val profit = anuallyProfit(date)
        val year = dateToYear(date)
        val yearSlider = IntArray(2){0}
        yearSlider[0]=year.toInt()
        yearSlider[1] = profit
        return yearSlider
    }
    fun dateToMonth( date : LocalDateTime) : String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val dateAndTimeString= date.format(formatter)
        val dateString = dateAndTimeString.substringBefore(" ")
        val dateMonthYear = dateString.substringAfter("/")
        val dateMonth = dateMonthYear.substringBefore("/")
        when(dateMonth){
            "1"->return "Gennaio"
            "2"->return "Febbraio"
            "3"->return "Marzo"
            "4"->return "Aprile"
            "5"->return "Maggio"
            "6"->return "Giugno"
            "7"->return "Luglio"
            "8"->return "Agosto"
            "9"->return "Settembre"
            "10"->return "Ottobre"
            "11"->return "Novembre"
            "12"->return "Dicembre"
            else ->{
                return "A risotto una problema"
            }
        }
    }
    fun dateToMonthYear (date : LocalDateTime) : String{
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val dateFormatted = date.format(formatter)
        val dateString = dateFormatted.substringBefore(" ")
        val dateMonthYear = dateString.substringAfter("/")
        return dateMonthYear
    }
    fun dateToYear ( date: LocalDateTime) : String{
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val dateAndTimeString= date.format(formatter)
        val dateString = dateAndTimeString.substringBefore(" ")
        val dateMonthYear = dateString.substringAfter("/")
        val dateYear = dateMonthYear.substringAfter("/")
        return dateYear
    }
    fun anuallyProfit(date: LocalDateTime ) : Int {
        var profit : Int = 0
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val dateAndTimeString= date.format(formatter)
        val dateString = dateAndTimeString.substringBefore(" ")
        val dateMonthYear = dateString.substringAfter("/")
        val year = dateMonthYear.substringAfter("/")
        for ( i in getItemsList())
        {
            val arrDateMonthYear = i.arrDate.substringAfter("/")
            val arrYear = arrDateMonthYear.substringAfter("/")
            val parDateMonthYear = i.parDate.substringAfter("/")
            val parYear = parDateMonthYear.substringAfter("/")
            if(arrYear == year && parYear == year){
                profit += i.price.toInt()
            }
            if(arrYear != year && parYear == year){
                val numNextStayed = i.parDate.substringBefore("/").toInt()-1
                val sdf = SimpleDateFormat("d/M/yyyy")
                val arrivalDate: Date = sdf.parse(i.arrDate)
                val partenzaDate: Date = sdf.parse(i.parDate)
                val numStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                val pricePerYear = (numNextStayed.toDouble() / numStayed.toDouble()) * i.price.toDouble()
                profit+=pricePerYear.toInt()
            }
            if(arrYear == year && parYear != year){
                val numNextStayed = i.parDate.substringBefore("/").toInt()-1
                val sdf = SimpleDateFormat("d/M/yyyy")
                val arrivalDate: Date = sdf.parse(i.arrDate)
                val partenzaDate: Date = sdf.parse(i.parDate)
                val numStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                val numStayedYear = numStayed - numNextStayed
                val pricePerYear = (numStayedYear.toDouble() / numStayed.toDouble()) * i.price.toDouble()
                profit+=pricePerYear.toInt()
            }

        }
        return profit
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
                var daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
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
    fun chartMonth(date : LocalDateTime) : IntArray{
        val sdf = SimpleDateFormat("d/M/yyyy")
        val year = dateToYear(date)
        val weekChart = IntArray( 31) { 0}
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val dateAndTimeString= date.format(formatter)
        val todayDateMonthYear = dateAndTimeString.substringAfter("/")
        val month = todayDateMonthYear.substringBefore("/")
        for( i in getItemsList())
        {
            val arrDateMonthYear = i.arrDate.substringAfter("/")
            val arrMonth = arrDateMonthYear.substringBefore("/")
            val parDateMonthYear = i.parDate.substringAfter("/")
            val parMonth = parDateMonthYear.substringBefore("/")
            val arrYear = arrDateMonthYear.substringAfter("/")
            val parYear = parDateMonthYear.substringAfter("/")
            val arrivalDate: Date = sdf.parse(i.arrDate)
            val partenzaDate: Date = sdf.parse(i.parDate)

            if(arrMonth == month && arrYear == year && parMonth == month && parYear == year)
            {
                val daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                val pricePerDay = i.price.toDouble() / daysStayed.toDouble()
                val startIndex = i.arrDate.substringBefore("/").toInt() -1
                val endIndex = i.parDate.substringBefore("/").toInt() -1
                for( j in 0 .. weekChart.size-1){
                    if( j >= startIndex&& j < endIndex)
                    {
                        weekChart[j]+=pricePerDay.toInt()
                    }
                }
            }
            if(arrMonth == month && parMonth != month && arrYear == year)
            {
                var daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                if(daysStayed == 0L){
                    daysStayed = 1L;
                }
                val pricePerDay = i.price.toDouble() / daysStayed.toDouble()
                val startIndex = i.arrDate.substringBefore("/").toInt()-1
                val endIndex = 31
                for( j in 0 .. weekChart.size-1){
                    if( j >= startIndex&& j< endIndex)
                    {
                        weekChart[j]+=pricePerDay.toInt()
                    }
                }
            }
            if(arrMonth != month && parMonth == month && parYear == year)
            {
                var daysStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                if(daysStayed == 0L){
                    daysStayed = 1L;
                }
                val pricePerDay = i.price.toDouble() / daysStayed.toDouble()
                val startIndex = 0
                val endIndex = i.parDate.substringBefore("/").toInt() -1
                for( j in 0 .. weekChart.size-1){
                    if( j >= startIndex && j< endIndex)
                    {
                        weekChart[j]+=pricePerDay.toInt()
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

        return weekChart
    }
    fun milisecondsToDays( initial: Long) : Long{
        val seconds = initial / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return days
    }
    fun profitSixMonths () : IntArray {
        val dateAndTime = LocalDateTime.now()
        val lastMonth1 = dateAndTime.minusMonths(1)
        val lastMonth2 = dateAndTime.minusMonths(2)
        val lastMonth3 = dateAndTime.minusMonths(3)
        val lastMonth4 = dateAndTime.minusMonths(4)
        val lastMonth5 = dateAndTime.minusMonths(5)
        val lastMonth6 = dateAndTime.minusMonths(6)

        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")
        val lastMonther1 = lastMonth1.format(formatter)
        val lastMonther2 = lastMonth2.format(formatter)
        val lastMonther3 = lastMonth3.format(formatter)
        val lastMonther4 = lastMonth4.format(formatter)
        val lastMonther5 = lastMonth5.format(formatter)
        val lastMonther6 = lastMonth6.format(formatter)

        val minusMonthDate1 = lastMonther1.substringBefore(" ")
        val minusMonthDate2 = lastMonther2.substringBefore(" ")
        val minusMonthDate3 = lastMonther3.substringBefore(" ")
        val minusMonthDate4 = lastMonther4.substringBefore(" ")
        val minusMonthDate5 = lastMonther5.substringBefore(" ")
        val minusMonthDate6 = lastMonther6.substringBefore(" ")

        val minusMonth1 = minusMonthDate1.substringAfter("/")
        val minusMonth2 = minusMonthDate2.substringAfter("/")
        val minusMonth3 = minusMonthDate3.substringAfter("/")
        val minusMonth4 = minusMonthDate4.substringAfter("/")
        val minusMonth5 = minusMonthDate5.substringAfter("/")
        val minusMonth6 = minusMonthDate6.substringAfter("/")

        var lastMonths6 = IntArray(6){0}
        lastMonths6[0] = pricePerMonth(minusMonth1)
        lastMonths6[1] = pricePerMonth(minusMonth2)
        lastMonths6[2] = pricePerMonth(minusMonth3)
        lastMonths6[3] = pricePerMonth(minusMonth4)
        lastMonths6[4] = pricePerMonth(minusMonth5)
        lastMonths6[5] = pricePerMonth(minusMonth6)
        return lastMonths6
    }
    fun pricePerMonth( equalMonth: String) : Int {
        var monthPrice : Double = 0.0
        for( i in getItemsList())
        {
            if(i.arrDate.substringAfter("/") == equalMonth && i.parDate.substringAfter("/")==equalMonth)
            {
                monthPrice+=i.price.toDouble()
            }
            if( i.parDate.substringAfter("/")==equalMonth && i.arrDate.substringAfter("/") != equalMonth)
            {
                val numStayedInMonth = i.parDate.substringBefore("/").toInt() -1
                val sdf = SimpleDateFormat("d/M/yyyy")
                val arrivalDate: Date = sdf.parse(i.arrDate)
                val partenzaDate: Date = sdf.parse(i.parDate)
                val numStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                val price =  (numStayedInMonth.toDouble() / numStayed.toDouble() ) * i.price.toDouble()
                monthPrice+=price.toDouble()

            }
            if( i.parDate.substringAfter("/")!=equalMonth && i.arrDate.substringAfter("/") == equalMonth)
            {
                val numStayedInNextMonth = i.parDate.substringBefore("/").toInt()-1
                val sdf = SimpleDateFormat("d/M/yyyy")
                val arrivalDate: Date = sdf.parse(i.arrDate)
                val partenzaDate: Date = sdf.parse(i.parDate)
                val numStayed = milisecondsToDays(partenzaDate.time - arrivalDate.time)
                val numStayedInMonth = numStayed - numStayedInNextMonth
                val price : Double =  (numStayedInMonth.toDouble() / numStayed.toDouble() ) * i.price.toDouble()
                monthPrice+=price.toDouble()
            }
        }
        return monthPrice.toInt()
    }
    fun methodPercentage() : DoubleArray{
        var booking : Double = 0.0
        var bnb : Double = 0.0
        var direct : Double  = 0.0

        for( i in getItemsList())
        {
              when(i.method){
                  "Booking.com" -> booking++
                  "airbnb.com" -> bnb++
                  "Diretta" -> direct++
                  else -> {
                      print("no method found")
                  }
              }
        }
        var sum : Double = booking + bnb + direct
        var bookingPercent: Double =0.0
        if( booking != 0.0)
        {
            bookingPercent = (( booking / sum) *100).roundToInt().toDouble()
        }
        else{
            bookingPercent = 0.0
        }
        var bnbPercent : Double =0.0
        if( bnb != 0.0)
        {
            bnbPercent = ((bnb / sum) *100).roundToInt().toDouble()
        }
        else{
            bnbPercent = 0.0
        }
        var directPercent : Double =0.0
        if( direct != 0.0)
        {
            directPercent = (( direct / sum) *100).roundToInt().toDouble()
        }
        else{
            directPercent = 0.0
        }
        var methodPercentage : DoubleArray = doubleArrayOf(bookingPercent, bnbPercent, directPercent)
        return methodPercentage

    }
    fun getItemsList():  ArrayList<OspModelClass>{
        val databaseHandler : DatabaseHandler = DatabaseHandler(requireActivity())
        val osp : ArrayList<OspModelClass> =databaseHandler.viewOspiti()
        return osp
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FinanceFragment().apply {
            }
    }
}