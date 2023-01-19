package com.android.bookdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var mainFragment: MainFragment
    lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()

        supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation) //왜안돼
    }
}