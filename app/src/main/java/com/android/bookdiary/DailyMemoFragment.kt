package com.android.bookdiary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction


class DailyMemoFragment : Fragment() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_daily_memo, container, false)

        // 책 다시 선택하도록 돌아가는 버튼
        val dailyBackBtn = view.findViewById<Button>(R.id.dailyBackBtn)
        dailyBackBtn.setOnClickListener{
            val dailyFragment = DailyFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, dailyFragment)
            transaction.commit()
        }

        // 작성 완료 버튼
        val dailyDoneBtn = view.findViewById<Button>(R.id.dailyDoneBtn)
        dailyDoneBtn.setOnClickListener{
            val mainFragment = MainFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, mainFragment)
            transaction.commit()
        }

        return view


    }
}
