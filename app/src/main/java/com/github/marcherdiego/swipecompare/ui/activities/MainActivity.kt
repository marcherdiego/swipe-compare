package com.github.marcherdiego.swipecompare.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.marcherdiego.swipecompare.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.horizontal_compare_button).setOnClickListener {
            startActivity(Intent(this, HorizontalSwipeActivity::class.java))
        }

        findViewById<View>(R.id.vertical_compare_button).setOnClickListener {
            startActivity(Intent(this, VerticalSwipeActivity::class.java))
        }

        findViewById<View>(R.id.crosshair_compare_button).setOnClickListener {
            startActivity(Intent(this, CrosshairSwipeActivity::class.java))
        }
    }
}
