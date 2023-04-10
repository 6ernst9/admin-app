package com.example.finance

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_calendar, container, false)
        val titleCalendar : TextView = view.findViewById(R.id.titleCalendar)
        val frameLayout : FrameLayout = view.findViewById(R.id.calendarFrame)
        if(titleCalendar.currentTextColor == resources.getColor(R.color.white)){
            frameLayout.background = resources.getDrawable(R.drawable.darkwall)
        }
        else{
            frameLayout.background = resources.getDrawable(R.drawable.big)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scrollView  = getView()?.findViewById<ScrollView>(R.id.calendarScroll)
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

        val upBtn1 = getView()?.findViewById<ImageView>(R.id.upBtn1)
        val expandableLayout1 = getView()?.findViewById<RelativeLayout>(R.id.expandableLayout1)
        val upBtn2 = getView()?.findViewById<ImageView>(R.id.upBtn2)
        val expandableLayout2 = getView()?.findViewById<RelativeLayout>(R.id.expandableLayout2)
        val upBtn3 = getView()?.findViewById<ImageView>(R.id.upBtn3)
        val expandableLayout3 = getView()?.findViewById<RelativeLayout>(R.id.expandableLayout3)
        val upBtn4 = getView()?.findViewById<ImageView>(R.id.upBtn4)
        val expandableLayout4 = getView()?.findViewById<RelativeLayout>(R.id.expandableLayout4)
        val upBtn5 = getView()?.findViewById<ImageView>(R.id.upBtn5)
        val expandableLayout5 = getView()?.findViewById<RelativeLayout>(R.id.expandableLayout5)
        val upBtn6 = getView()?.findViewById<ImageView>(R.id.upBtn6)
        val expandableLayout6 = getView()?.findViewById<RelativeLayout>(R.id.expandableLayout6)
        val deleteBtn1 = getView()?.findViewById<ImageView>(R.id.deleteBtn1)
        val deleteBtn2 = getView()?.findViewById<ImageView>(R.id.deleteBtn2)
        val deleteBtn3 = getView()?.findViewById<ImageView>(R.id.deleteBtn3)
        val deleteBtn4 = getView()?.findViewById<ImageView>(R.id.deleteBtn4)
        val deleteBtn5 = getView()?.findViewById<ImageView>(R.id.deleteBtn5)
        val deleteBtn6 = getView()?.findViewById<ImageView>(R.id.deleteBtn6)
        val cardview1 : ConstraintLayout? = view?.findViewById(R.id.cardview1)
        val cardview2 : ConstraintLayout? = view?.findViewById(R.id.cardview2)
        val cardview3 : ConstraintLayout? = view?.findViewById(R.id.cardview3)
        val cardview4 : ConstraintLayout? = view?.findViewById(R.id.cardview4)
        val cardview5 : ConstraintLayout? = view?.findViewById(R.id.cardview5)
        val cardview6 : ConstraintLayout? = view?.findViewById(R.id.cardview6)
        val id1 : TextView? = view?.findViewById(R.id.cardview1id)
        val id2 : TextView? = view?.findViewById(R.id.cardview2id)
        val id3 : TextView? = view?.findViewById(R.id.cardview3id)
        val id4 : TextView? = view?.findViewById(R.id.cardview4id)
        val id5 : TextView? = view?.findViewById(R.id.cardview5id)
        val id6 : TextView? = view?.findViewById(R.id.cardview6id)
        val arrivalLogo1 : ImageView? = view?.findViewById(R.id.arrivaLogo1)
        val arrivalLogo2 : ImageView? = view?.findViewById(R.id.arrivaLogo2)
        val partenzaLogo1 : ImageView? = view?.findViewById(R.id.arrivaLogo3)
        val partenzaLogo2 : ImageView? = view?.findViewById(R.id.arrivaLogo4)
        val ocupatLogo1 : ImageView? = view?.findViewById(R.id.arrivaLogo5)
        val ocupatLogo2 : ImageView? = view?.findViewById(R.id.arrivaLogo6)

        upBtn1?.setOnClickListener{ view ->
            expandableLayout1?.isVisible = false
            arrivalLogo1?.isVisible = true
            deleteBtn1?.isVisible = false
        }
        cardview1?.setOnClickListener{ view ->
            expandableLayout1?.isVisible = true
            arrivalLogo1?.isVisible = false
            deleteBtn1?.isVisible = true

        }
        upBtn2?.setOnClickListener{ view ->
            expandableLayout2?.isVisible = false
            arrivalLogo2?.isVisible = true
            deleteBtn2?.isVisible = false
        }

        cardview2?.setOnClickListener{ view ->
            expandableLayout2?.isVisible = true
            arrivalLogo2?.isVisible = false
            deleteBtn2?.isVisible = true
        }
        upBtn3?.setOnClickListener{ view ->
            expandableLayout3?.isVisible = false
            partenzaLogo1?.isVisible = true
            deleteBtn3?.isVisible = false
        }
        cardview3?.setOnClickListener{ view ->
            expandableLayout3?.isVisible = true
            partenzaLogo1?.isVisible = false
            deleteBtn3?.isVisible = true

        }
        upBtn4?.setOnClickListener{ view ->
            expandableLayout4?.isVisible = false
            partenzaLogo2?.isVisible = true
            deleteBtn4?.isVisible = false

        }
        cardview4?.setOnClickListener{ view ->
            expandableLayout4?.isVisible = true
            partenzaLogo2?.isVisible = false
            deleteBtn4?.isVisible = true

        }
        upBtn5?.setOnClickListener{ view ->
            expandableLayout5?.isVisible = false
            ocupatLogo1?.isVisible = true
            deleteBtn5?.isVisible = false
        }
        cardview5?.setOnClickListener{ view ->
            expandableLayout5?.isVisible = true
            ocupatLogo1?.isVisible = false
            deleteBtn5?.isVisible = true
        }
        upBtn6?.setOnClickListener{ view ->
            expandableLayout6?.isVisible = false
            ocupatLogo2?.isVisible = true
            deleteBtn6?.isVisible = false
        }
        cardview6?.setOnClickListener{ view ->
            expandableLayout6?.isVisible = true
            ocupatLogo2?.isVisible = false
            deleteBtn6?.isVisible = true

        }

        deleteBtn1?.setOnClickListener{ view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.delete_layout, null)
            val builder = AlertDialog.Builder(activity).setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deleteCancel).setOnClickListener{
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.deleteDelete).setOnClickListener{
                val selectedId = id1?.text
                for( i in getItemsList())
                {
                    if(i.id.toString() == selectedId )
                    {
                        deletePrenotation(i)
                    }
                    else{
                        println("Not found")
                        println(selectedId)
                    }
                }
                alertDialog.dismiss()
            }
        }
        deleteBtn2?.setOnClickListener{ view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.delete_layout, null)
            val builder = AlertDialog.Builder(activity).setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deleteCancel).setOnClickListener{
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.deleteDelete).setOnClickListener{
                val selectedId = id2?.text
                for( i in getItemsList())
                {
                    if(i.id.toString() == selectedId )
                    {
                        deletePrenotation(i)
                    }
                    else{
                        println("Not found")
                    }
                }
                alertDialog.dismiss()
            }
        }
        deleteBtn3?.setOnClickListener{ view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.delete_layout, null)
            val builder = AlertDialog.Builder(activity).setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deleteCancel).setOnClickListener{
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.deleteDelete).setOnClickListener{
                val selectedId = id3?.text
                for( i in getItemsList())
                {
                    if(i.id.toString() == selectedId )
                    {
                        deletePrenotation(i)
                    }
                    else{
                        println("Not found")
                        println(selectedId)
                    }
                }
                alertDialog.dismiss()
            }
        }
        deleteBtn4?.setOnClickListener{ view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.delete_layout, null)
            val builder = AlertDialog.Builder(activity).setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deleteCancel).setOnClickListener{
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.deleteDelete).setOnClickListener{
                val selectedId = id4?.text
                for( i in getItemsList())
                {
                    if(i.id.toString() == selectedId )
                    {
                        deletePrenotation(i)
                    }
                    else{
                        println("Not found")
                        println(selectedId)
                    }
                }
                alertDialog.dismiss()
            }
        }
        deleteBtn5?.setOnClickListener{ view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.delete_layout, null)
            val builder = AlertDialog.Builder(activity).setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deleteCancel).setOnClickListener{
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.deleteDelete).setOnClickListener{
                val selectedId = id5?.text
                for( i in getItemsList())
                {
                    if(i.id.toString() == selectedId )
                    {
                        deletePrenotation(i)
                    }
                    else{
                        println("Not found")
                        println(selectedId)
                    }
                }
                alertDialog.dismiss()
            }
        }
        deleteBtn6?.setOnClickListener{ view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.delete_layout, null)
            val builder = AlertDialog.Builder(activity).setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deleteCancel).setOnClickListener{
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.deleteDelete).setOnClickListener{
                val selectedId = id6?.text
                for( i in getItemsList())
                {
                    if(i.id.toString() == selectedId )
                    {
                        deletePrenotation(i)
                    }
                    else{
                        println("Not found")
                        println(selectedId)
                    }
                }
                alertDialog.dismiss()
            }
        }
        val newBtn = getView()?.findViewById<ImageView>(R.id.addBtn)
        newBtn?.setOnClickListener{ view ->
            val intent = Intent( activity, AddActivity::class.java)
            startActivity( intent )
        }
        todayCalendar()
    }
    fun todayCalendar(){
        val calendarView : CalendarView? =view?.findViewById(R.id.calendarView)
        val dateAndTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss.SSS")

        val formatted = dateAndTime.format(formatter)
        val date = formatted.substringBefore(" ")
        val title1 : TextView? = view?.findViewById(R.id.cardviewTitle1)
        val guests1 : TextView? = view?.findViewById(R.id.cardview1Guest)
        val dates1 : TextView? = view?.findViewById(R.id.cardview1Dates)
        val title2 : TextView? = view?.findViewById(R.id.cardviewTitle2)
        val numGuest2 : TextView? = view?.findViewById(R.id.cardview2NumOfGuests)
        val guests2 : TextView? = view?.findViewById(R.id.cardview2Guest)
        val dates2 : TextView? = view?.findViewById(R.id.cardview2Dates)
        val title3 : TextView? = view?.findViewById(R.id.cardviewTitle3)
        val numGuest3 : TextView? = view?.findViewById(R.id.cardview3NumOfGuests)
        val guests3 : TextView? = view?.findViewById(R.id.cardview3Guest)
        val dates3 : TextView? = view?.findViewById(R.id.cardview3Dates)
        val title4 : TextView? = view?.findViewById(R.id.cardviewTitle4)
        val numGuest4 : TextView? = view?.findViewById(R.id.cardview4NumOfGuests)
        val guests4 : TextView? = view?.findViewById(R.id.cardview4Guest)
        val dates4 : TextView? = view?.findViewById(R.id.cardview4Dates)
        val title5 : TextView? = view?.findViewById(R.id.cardviewTitle5)
        val numGuest5 : TextView? = view?.findViewById(R.id.cardview5NumOfGuests)
        val guests5 : TextView? = view?.findViewById(R.id.cardview5Guest)
        val dates5 : TextView? = view?.findViewById(R.id.cardview5Dates)
        val title6 : TextView? = view?.findViewById(R.id.cardviewTitle6)
        val numGuest6 : TextView? = view?.findViewById(R.id.cardview6NumOfGuests)
        val guests6 : TextView? = view?.findViewById(R.id.cardview6Guest)
        val dates6 : TextView? = view?.findViewById(R.id.cardview6Dates)
        val phone1: TextView? = view?.findViewById(R.id.cardview1Phone)
        val phone2: TextView? = view?.findViewById(R.id.cardview2Phone)
        val phone3: TextView? = view?.findViewById(R.id.cardview3Phone)
        val phone4: TextView? = view?.findViewById(R.id.cardview4Phone)
        val phone5: TextView? = view?.findViewById(R.id.cardview5Phone)
        val phone6: TextView? = view?.findViewById(R.id.cardview6Phone)
        val id1 : TextView? = view?.findViewById(R.id.cardview1id)
        val id2 : TextView? = view?.findViewById(R.id.cardview2id)
        val id3 : TextView? = view?.findViewById(R.id.cardview3id)
        val id4 : TextView? = view?.findViewById(R.id.cardview4id)
        val id5 : TextView? = view?.findViewById(R.id.cardview5id)
        val id6 : TextView? = view?.findViewById(R.id.cardview6id)
        val cardview1 : ConstraintLayout? = view?.findViewById(R.id.cardview1)
        val cardview2 : ConstraintLayout? = view?.findViewById(R.id.cardview2)
        val cardview3 : ConstraintLayout? = view?.findViewById(R.id.cardview3)
        val cardview4 : ConstraintLayout? = view?.findViewById(R.id.cardview4)
        val cardview5 : ConstraintLayout? = view?.findViewById(R.id.cardview5)
        val cardview6 : ConstraintLayout? = view?.findViewById(R.id.cardview6)
        val noTasks : ConstraintLayout?= view?.findViewById(R.id.noTask)
        val price1 : TextView? = view?.findViewById(R.id.cardview1Price)
        val price2 : TextView? = view?.findViewById(R.id.cardview2Price)
        val price3 : TextView? = view?.findViewById(R.id.cardview3Price)
        val price4 : TextView? = view?.findViewById(R.id.cardview4Price)
        val price5 : TextView? = view?.findViewById(R.id.cardview5Price)
        val price6 : TextView? = view?.findViewById(R.id.cardview6Price)
        var loop=0
        var counter = 0
        var et = 0
        cardview1?.isVisible = false
        cardview2?.isVisible = false
        cardview3?.isVisible = false
        cardview4?.isVisible = false
        cardview5?.isVisible = false
        cardview6?.isVisible = false
        for( i in getItemsList()) {
            if(i.arrDate == date) {

                if(loop==0){
                    title1?.text = "Arriva ${i.house}"
                    guests1?.text = i.names
                    dates1?.text = "${i.arrDate} - ${i.parDate}"
                    phone1?.text = i.phone
                    cardview1?.isVisible = true
                    id1?.text = "$i.id"
                    price1?.text = "+${i.price}€"

                }
                if(loop==1){
                    title2?.text = "Arriva ${i.house}"
                    numGuest2?.text = i.numberOsp
                    guests2?.text = i.names
                    dates2?.text = "${i.arrDate} - ${i.parDate}"
                    phone2?.text = i.phone
                    cardview2?.isVisible = true
                    id2?.text = "$i.id"
                    price2?.text = "+${i.price}€"
                }
                loop++
            }
            if(i.parDate == date) {

                if(counter==0){
                    title3?.text = "Partenza ${i.house}"
                    numGuest3?.text = i.numberOsp
                    guests3?.text = i.names
                    dates3?.text = "${i.arrDate} - ${i.parDate}"
                    phone3?.text = i.phone
                    cardview3?.isVisible = true
                    id3?.text = "$i.id"
                    price3?.text = "+${i.price}€"


                }
                if(counter==1){
                    title4?.text = "Partenza ${i.house}"
                    numGuest4?.text = i.numberOsp
                    guests4?.text = i.names
                    phone4?.text = i.phone
                    dates4?.text = "${i.arrDate} - ${i.parDate}"
                    cardview4?.isVisible = true
                    id4?.text = "$i.id"
                    price4?.text = "+${i.price}€"
                }
                counter++
            }
            var d1 = i.arrDate
            var d2 = i.parDate
            var d3 = date
            val sdf = SimpleDateFormat("d/M/yyyy")
            val arrivalDate : Date = sdf.parse(d1)
            val partDate : Date = sdf.parse(d2)
            val todaysDate : Date = sdf.parse(d3)

            if(arrivalDate.compareTo(todaysDate)<0 && todaysDate.compareTo(partDate) < 0) {

                if(et==0){
                    title5?.text = "Ocupatto ${i.house}"
                    numGuest5?.text = i.numberOsp
                    guests5?.text = i.names
                    dates5?.text = "${i.arrDate} - ${i.parDate}"
                    phone5?.text = i.phone
                    cardview5?.isVisible = true
                    id5?.text = "$i.id"
                    price5?.text = "+${i.price}€"

                }
                if(et==1){
                    title6?.text = "Ocupatto ${i.house}"
                    numGuest6?.text = i.numberOsp
                    guests6?.text = i.names
                    phone6?.text = i.phone
                    dates6?.text = "${i.arrDate} - ${i.parDate}"
                    cardview6?.isVisible = true
                    id6?.text = "$i.id"
                    price6?.text = "+${i.price}€"
                }
                et++
            }
        }
        noTasks?.isVisible = cardview1?.isVisible == false && cardview2?.isVisible == false && cardview3?.isVisible == false &&cardview4?.isVisible == false && cardview5?.isVisible == false &&cardview6?.isVisible == false

        calendarView!!.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val day = dayOfMonth.toString()
            val year = year.toString()
            val month = (month+1).toString()
            val date = "$day/$month/$year"
            var loop = 0
            var counter = 0
            var et = 0
            cardview1?.isVisible = false
            cardview2?.isVisible = false
            cardview3?.isVisible = false
            cardview4?.isVisible = false
            cardview5?.isVisible = false
            cardview6?.isVisible = false
            for( i in getItemsList()) {
                if(i.arrDate == date) {

                    if(loop==0){
                        title1?.text = "Arriva ${i.house}\n${i.numberOsp}"
                        guests1?.text = i.names
                        dates1?.text = "${i.arrDate} - ${i.parDate}"
                        cardview1?.isVisible = true
                        id1?.text = i.id.toString()
                        price1?.text = "+${i.price}€"

                    }
                    if(loop==1){
                        title2?.text = "Arriva ${i.house}"
                        numGuest2?.text = i.numberOsp
                        guests2?.text = i.names
                        dates2?.text = "${i.arrDate} - ${i.parDate}"
                        cardview2?.isVisible = true
                        id2?.text = i.id.toString()
                        price2?.text = "+${i.price}€"
                    }
                    loop++
                }
                if(i.parDate == date) {

                    if(counter==0){
                        title3?.text = "Partenza ${i.house}"
                        numGuest3?.text = i.numberOsp
                        guests3?.text = i.names
                        dates3?.text = "${i.arrDate} - ${i.parDate}"
                        cardview3?.isVisible = true
                        id3?.text = i.id.toString()
                        price3?.text = "+${i.price}€"

                    }
                    if(counter==1){
                        title4?.text = "Partenza ${i.house}"
                        numGuest4?.text = i.numberOsp
                        guests4?.text = i.names
                        dates4?.text = "${i.arrDate} - ${i.parDate}"
                        cardview4?.isVisible = true
                        id4?.text = i.id.toString()
                        price4?.text = "+${i.price}€"
                    }
                    counter++
                }
                var d1 = i.arrDate
                var d2 = i.parDate
                var d3 = date
                val sdf = SimpleDateFormat("d/M/yyyy")
                val arrivalDate : Date = sdf.parse(d1)
                val partDate : Date = sdf.parse(d2)
                val todaysDate : Date = sdf.parse(d3)

                if(arrivalDate.compareTo(todaysDate)<0 && todaysDate.compareTo(partDate) < 0) {

                    if(et==0){
                        title5?.text = "Ocupatto ${i.house}"
                        numGuest5?.text = i.numberOsp
                        guests5?.text = i.names
                        dates5?.text = "${i.arrDate} - ${i.parDate}"
                        cardview5?.isVisible = true
                        id5?.text = i.id.toString()
                        price5?.text = "+${i.price}€"

                    }
                    if(et==1){
                        title6?.text = "Ocupatto ${i.house}"
                        numGuest6?.text = i.numberOsp
                        guests6?.text = i.names
                        dates6?.text = "${i.arrDate} - ${i.parDate}"
                        cardview6?.isVisible = true
                        id6?.text = i.id.toString()
                        price6?.text = "+${i.price}€"
                    }
                    et++
                }
            }
            noTasks?.isVisible = cardview1?.isVisible == false && cardview2?.isVisible == false && cardview3?.isVisible == false &&cardview4?.isVisible == false && cardview5?.isVisible == false &&cardview6?.isVisible == false

        }


    }

    fun deletePrenotation(ospModelClass: OspModelClass){
        val databaseHandler : DatabaseHandler = DatabaseHandler(requireActivity())
        val status = databaseHandler.deleteOspiti(OspModelClass( ospModelClass.id, "", "", "", "", "", "", "", ""))
        if (status> -1){
            Toast.makeText(requireContext(), "Prenotazione eliminata", Toast.LENGTH_LONG).show()
        }
    }

    fun getItemsList():  ArrayList<OspModelClass>{
        val databaseHandler : DatabaseHandler = DatabaseHandler(requireActivity())
        val osp : ArrayList<OspModelClass> =databaseHandler.viewOspiti()
        return osp
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}