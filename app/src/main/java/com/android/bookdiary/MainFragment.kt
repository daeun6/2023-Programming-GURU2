package com.android.bookdiary

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bookdiary.databinding.FragmentMainBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MainFragment : Fragment(), OnitemListener {

    private lateinit var binding : FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtil.selectedDate = LocalDate.now()
        }
        setMonthView()

        binding.preBtn.setOnClickListener {// 이전달 버튼
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1)
            }
            setMonthView()

        }

        binding.nextBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1)
            }
            setMonthView()

        }

        return binding.root

    }

    private fun setMonthView() {

        binding.monthyearText.text = monthyearFromDate(CalendarUtil.selectedDate) //년 월 가져옴

        val dayList = dayInMonthArray(CalendarUtil.selectedDate) // 날짜 생성 후 리스트에 담음

        // 책 그림도 날짜에 맞게 리스트에 담겨야함 어떻게 구현하지
        //dayList 사이즈만큼 그림 생성해서 id를 부여한다고 하자
        val adapter = CalendarAdapter(dayList, this)

        var manager : RecyclerView.LayoutManager = GridLayoutManager(activity, 7)

        binding.recyclerView.layoutManager = manager //레이아웃 적용

        binding.recyclerView.adapter = adapter

    }

    // 날짜 타입 설정 -> 월, 년도
    private fun monthyearFromDate(date: LocalDate) : String{
        var formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("MM월 yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        return date.format(formatter)
    }

    //날짜 타입 -> 년도, 월
    private fun yearmonthFromDate(date: LocalDate) : String{
        var formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy년 MM월")
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        return date.format(formatter)
    }

    private fun dayInMonthArray(date: LocalDate) : ArrayList<String>{
        var dayList = ArrayList<String>()

        var yearMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.from(date)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        var lastDay = yearMonth.lengthOfMonth() //해당 월 마지막 날짜 가져옴
        var firstDay = CalendarUtil.selectedDate.withDayOfMonth(1) //해당 월 첫번째 날짜 가져옴
        var dayOfweek = firstDay.dayOfWeek.value // 첫번째 날 요일 가져옴
        for(i in 1..41) {
            if(i <= dayOfweek || i > (lastDay + dayOfweek)){
                dayList.add("")
            }else{
                dayList.add((i-dayOfweek).toString())
            }
        }

        return dayList
    }



    // 아이템 클릭 이벤트
    override fun onItemClick(dayText: String) {
        var yearMonth = yearmonthFromDate(CalendarUtil.selectedDate) + " " +dayText + "일"

        //여기서 뷰 홀더를 가져와서 사용하는 방법은 없나?
        Toast.makeText(activity, yearMonth, Toast.LENGTH_SHORT).show()

    }

}


