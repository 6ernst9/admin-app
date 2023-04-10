package com.example.finance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newTitle : TextView = findViewById(R.id.textView)
        val frameLayout : ConstraintLayout = findViewById(R.id.welcomeFrame)
        val logo : ImageView = findViewById(R.id.createdByLogo)
        if(newTitle.currentTextColor == resources.getColor(R.color.white)){
            frameLayout.background = resources.getDrawable(R.drawable.darkwall)
            logo.setImageDrawable(resources.getDrawable(R.drawable.weblogo))
        }
        else{
            frameLayout.background = resources.getDrawable(R.drawable.big)
            logo.setImageDrawable(resources.getDrawable(R.drawable.logo))
        }
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, ScreenActivity::class.java)
            startActivity(intent)
        }, 1000)

    }
}