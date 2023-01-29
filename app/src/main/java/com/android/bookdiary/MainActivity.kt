/*
어시슈트 - 소북소북 코드입니다.

정보보호학과 2020111323 김지원
정보보호학과 2021111325 김해린
정보보호학과 2021111336 송다은(팀 대표)
정보보호학과 2021111694 이가연

 */

package com.android.bookdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.tab1 -> {
                val monthlyFragment = MonthlyFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, monthlyFragment)
                    .commit()
            }
            R.id.tab2 -> {
                val mainFragment = MainFragment()

                supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment)
                    .commit()
            }
            R.id.tab3 -> {
                val listFragment = ListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, listFragment)
                    .commit()
            }
        }
        return true

    }
}




