package com.android.bookdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    class MainActivity : AppCompatActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener {


        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val mainFragment = MainFragment()
            //      val listFragment = ListFragment()


            // 가연 프래그먼트 연결
            val monthlyFragment = MonthlyFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()
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
                    //        val listFragment = ListFragment()
                    //        supportFragmentManager.beginTransaction().replace(R.id.container, listFragment)
                    //           .commit()

                }

            }

            return true

        }
    }
}

