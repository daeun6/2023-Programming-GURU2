package com.android.bookdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainFragment = MainFragment()
        val dailyFragment = DailyFragment()
        supportFragmentManager.beginTransaction().add(R.id.container, mainFragment).commit()
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tab1 -> {
                val monthlyFragment = MonthlyFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, monthlyFragment)
                    .commit()
            }
            R.id.tab2 -> {
                val mainFragment = MainFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment)
                    .commit()
            }
            R.id.tab3 -> {
                val dailyFragment = DailyFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, dailyFragment)
                    .commit()
            }
        }
        return true
    }
}

