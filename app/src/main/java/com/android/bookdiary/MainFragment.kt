package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bookdiary.databinding.FragmentMainBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MainFragment : Fragment(), OnitemListener {

    private lateinit var binding : FragmentMainBinding
    lateinit var dbManager : DBManager
    lateinit var sqlitedb : SQLiteDatabase
    var TAG : String = "메인 프래그먼트"

    val calendarDataArry : ArrayList<CalendarData> = ArrayList()






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
//DDD
        }

        binding.nextBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1)
            }
            setMonthView()

        }

        return binding.root

    }

    @SuppressLint("Range")
    private fun setMonthView() {

        binding.monthyearText.text = monthyearFromDate(CalendarUtil.selectedDate) //년 월 가져옴

        val dayList = dayInMonthArray(CalendarUtil.selectedDate) // 날짜 생성 후 리스트에 담음

        dbManager = DBManager(activity, "writeDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM writeDB;", null)



        // 책 그림도 날짜에 맞게 리스트에 담겨야함 어떻게 구현하지
        //dayList 사이즈만큼 그림 생성해서 id를 부여한다고 하자\*/

        val adapter = CalendarAdapter(dayList, this, calendarDataArry)
        Log.d(TAG,"어댑터 생성 ")

        var manager : RecyclerView.LayoutManager = GridLayoutManager(activity, 7)


        binding.recyclerView.layoutManager = manager //레이아웃 적용
        Log.d(TAG,"레이아웃 적ㅇ용 ")

        binding.recyclerView.adapter = adapter


        //sqlitedb.execSQL("INSERT INTO writeDB VALUES ('aa', '80', '라', '굿ㅋ', '2023년 01월 23일', '아', 'ORANGE', 100)")

       // sqlitedb.execSQL("INSERT INTO bookDB VALUES ('BLUE', 'aa', '가', '찬희', 426, 226, 226, '2023년 2월 16일')")



        while(cursor.moveToNext()){
            Log.d(TAG,"while문 들어옴 ")
            var str_date = cursor.getString((cursor.getColumnIndex("dDate")))
            var str_color = cursor.getString((cursor.getColumnIndex("dColor")))
            var totalPage = cursor.getInt(cursor.getColumnIndex("dTotalPage"))
            var nowPage = cursor.getInt(cursor.getColumnIndex("dNowPage"))
            var ratioPageFloat : Float = (nowPage.toFloat() / totalPage.toFloat())
            var ratioPage : Int = (ratioPageFloat * 100).toInt()
            Log.d(TAG,"$str_color")
            var data : CalendarData = CalendarData(str_date, str_color, ratioPage)
            calendarDataArry.add(data)

        }

        dbManager.close()
        cursor.close()
        sqlitedb.close()




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

    private fun dayInMonthArray(date: LocalDate) : ArrayList<LocalDate?>{
        var dayList = ArrayList<LocalDate?>()

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
                dayList.add(null)
            }else{
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.year,
                CalendarUtil.selectedDate.monthValue, i-dayOfweek))
            }
        }

        return dayList
    }



    // 아이템 클릭 이벤트

    override fun onItemClick(dayText: LocalDate) {
        // 글자 자르기

        var str_day : String = dayText.toString()
        var sub_year : String = str_day.substring(0 until 4)
        var sub_month : String = str_day.substring(5 until 7)
        var sub_day : String = str_day.substring(8 until 10)
        str_day = sub_year + "년 " + sub_month + "월 " + sub_day + "일"
        var bundle = Bundle()
        bundle.putString("key", str_day)
        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

        var DailyFragment = DailyFragment()
        DailyFragment.arguments = bundle

        ft.replace(R.id.container, DailyFragment).commit()

        // activity?.supportFragmentManager!!.beginTransaction().replace(R.id.container, DailyFragment()).commit()

        //여기서 뷰 홀더를 가져와서 사용하는 방법은 없나?
        Toast.makeText(activity, str_day, Toast.LENGTH_SHORT).show()

    }


}


