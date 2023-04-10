package com.example.finance

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var numForm : Spinner
    lateinit var bookForm : Spinner
    lateinit var bookMethod : String
    lateinit var numberGuests : String
    lateinit var casaRosa1 : Button
    lateinit var casaRosa2 : Button
    lateinit var casaAfitata : String
    lateinit var priceForm : EditText
    lateinit var price : String
    lateinit var namesForm : EditText
    lateinit var names : String
    lateinit var arrivalDate: String
    lateinit var partenzaDate : String
    lateinit var addBtn : Button
    lateinit var phoneNumber : EditText
    lateinit var phone: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_actvity)
        val datePicker1 =findViewById<EditText>(R.id.arrivalForm)
        val datePicker2 = findViewById<EditText>(R.id.partenzaForm)
        val newTitle : TextView = findViewById(R.id.newTitle)
        val frameLayout : ConstraintLayout = findViewById(R.id.newFrame)
        if(newTitle.currentTextColor == resources.getColor(R.color.white)){
            frameLayout.background = resources.getDrawable(R.drawable.darkwall)
        }
        else{
            frameLayout.background = resources.getDrawable(R.drawable.big)
        }

        priceForm = findViewById(R.id.priceForm) as EditText
        namesForm = findViewById(R.id.namesForm) as EditText
        phoneNumber = findViewById(R.id.phoneForm) as EditText


        numForm = findViewById(R.id.numForm) as Spinner
        bookForm = findViewById(R.id.bookForm) as Spinner

        val bookings = arrayOf("Booking.com", "airbnb.com", "Diretta")
        val numbers = arrayOf("1 Ospiti", "2 Ospiti", "3 Ospiti","4 Ospiti", "5 Ospiti", "6 Ospiti")

        numForm.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numbers)
        numForm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                numberGuests = numbers.get(p2)
                Log.i( "Succesful", "$numberGuests")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        bookForm.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookings)
        bookForm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bookMethod = bookings.get(p2)
                Log.i( "Succesful", "$bookMethod")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        casaRosa1 = findViewById<Button>(R.id.casaRosa1)
        casaRosa2 = findViewById<Button>(R.id.casaRosa2)
        casaRosa1.setOnClickListener{ view ->
            casaAfitata = "Casa Rosa 1"
            casaRosa1.setBackgroundResource(R.drawable.new_button_selected)
            casaRosa1.setTextColor(Color.parseColor("#EE07FF"))
            casaRosa2.setBackgroundResource(R.drawable.new_button_house)
            casaRosa2.setTextColor(Color.parseColor("#000000"))
        }
        casaRosa2.setOnClickListener{ view ->
            casaAfitata = "Casa Rosa 2"
            casaRosa2.setBackgroundResource(R.drawable.new_button_selected)
            casaRosa2.setTextColor(Color.parseColor("#EE07FF"))
            casaRosa1.setBackgroundResource(R.drawable.new_button_house)
            casaRosa1.setTextColor(Color.parseColor("#000000"))

        }
        val calendar = Calendar.getInstance()
        val year = calendar.get( Calendar.YEAR)
        val month = calendar.get( Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datePicker1.setOnClickListener{ view ->
            val datePickerArrival = DatePickerDialog ( this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                datePicker1.setText("$mDay/${mMonth+1}/$mYear")}, year, month, day)
            datePickerArrival.show()
        }
        datePicker2.setOnClickListener{ view ->
            val datePickerPartenza = DatePickerDialog ( this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                datePicker2.setText("$mDay/${mMonth+1}/$mYear")}, year, month, day)
            datePickerPartenza.show()
        }
        addBtn = findViewById(R.id.addBtn) as Button
        addBtn.setOnClickListener{ view ->
            price = priceForm.text.toString()
            names = namesForm.text.toString()
            phone = phoneNumber.text.toString()
            arrivalDate = datePicker1.text.toString()
            partenzaDate = datePicker2.text.toString()

            val databaseHandler : DatabaseHandler = DatabaseHandler(this)
            if(price!=""&& names!="" && arrivalDate!="" && partenzaDate!= ""&&numberGuests!=""&& bookMethod!=""&&casaAfitata!=""&&phone!=""){
                if( bookMethod == "Booking.com"){
                    val doublePrice = ((price.toDouble())*85/100 ).toInt()
                    price = doublePrice.toString()
                }
                if( bookMethod == "airbnb.com")
                {
                    val doublePrice = ((price.toDouble())*95/100 ).toInt()
                    price = doublePrice.toString()
                }
                val status = databaseHandler.addOspiti(OspModelClass(0, casaAfitata, numberGuests, price, names, arrivalDate, partenzaDate, bookMethod, phone))
                if(status>-1){
                    val intent = Intent( this@AddActivity, ScreenActivity::class.java)
                    startActivity( intent )
                    Log.i("Price", "$price")
                    Log.i("Names", "$names")
                    Log.i("Date of Arrival", "$arrivalDate")
                    Log.i("Date of Partenza", "$partenzaDate")
                    Log.i("Number of Guests", "$numberGuests")
                    Log.i("Booking Method", "$bookMethod")
                    Log.i("House Rented", "$casaAfitata")
                    Log.i("Phone Number", "$phone")
                    Toast.makeText(this@AddActivity, "Nova Prenotazione Creata!", Toast.LENGTH_LONG).show()

                }
            }
            else{
                Toast.makeText(this@AddActivity, "\n" + "Si prega di compilare tutti i moduli", Toast.LENGTH_LONG).show()
            }
        }
    }
}

