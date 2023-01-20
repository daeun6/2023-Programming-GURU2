package com.android.bookdiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.android.bookdiary.databinding.FragmentDailyBinding


class DailyFragment : Fragment() {

    private lateinit var binding : FragmentDailyBinding
    lateinit var str_day : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var dailyText : TextView


        val view = inflater.inflate(R.layout.fragment_daily, container, false)

        dailyText = view.findViewById(R.id.dailyText)

        if(arguments != null)
        {
            str_day = arguments?.getString("key").toString()
        }

        dailyText.setText(str_day)









        return view

    }


    }
