package com.android.bookdiary

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class MonthlyFragment : Fragment() {

//    private lateinit var barChart1: BarChart
//    private lateinit var barChart2: BarChart

    // 막대 그래프 데이터 변수
    private lateinit var barDataSet: BarDataSet

    // 스위치 변수
    private lateinit var switch1: Switch

    // 텍스트 변수
    private lateinit var bookRecord: TextView
    private lateinit var myBookRecord1: TextView
    private lateinit var myBookRecord2: TextView
    private lateinit var myBookRecord3: TextView
    private lateinit var one: TextView
    private lateinit var two: TextView


    // DB 관련 변수
    lateinit var myHelper: DBManager
    lateinit var sqlDB: SQLiteDatabase


    // DB 생성
 /*   inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "bookDB", null, 1){
        override fun onCreate(db: SQLiteDatabase?) {
            // 테이블 생성
            db!!.execSQL("CREATE TABLE bookDB ( color CHAR(20) NOT NULL, id CHAR(50) NOT NULL, title CHAR(60) PRIMARY KEY NOT NULL, author CHAR(30) NOT NULL, totalPage INTEGER NOT NULL, nowPage INTEGER, accumPage INTEGER, data CHAR(30))")
            db!!.execSQL("CREATE TABLE readingDB ( category CHAR(50), cases INTEGER, totalAvg INTEGER, readAvg INTEGER)")
            db!!.execSQL("CREATE TABLE userDB ( user CHAR(20) PRIMARY KEY, id CHAR(20), password CHAR(20))")
            db!!.execSQL("CREATE TABLE writeDB ( dUser CHAR(30), dNowPage INTEGER, dSentence CHAR(100), dThink CHAR(100), dDate CHAR(20), dTitle CHAR(30), dColor CHAR(20), totalPage INTEGER)")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            // 테이블 삭제 후 생성
            db!!.execSQL("DROP TABLE IF EXISTS bookDB")
            db!!.execSQL("DROP TABLE IF EXISTS readingDB")
            db!!.execSQL("DROP TABLE IF EXISTS userDB")
            db!!.execSQL("DROP TABLE IF EXISTS writeDB")
         //  onCreate(db)
        }
    }*/





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_monthly, container, false)

        // 레이아웃의 barChart와 연결
        var barChart1 = view.findViewById<BarChart>(R.id.barChart1)
        var barChart2 = view.findViewById<BarChart>(R.id.barChart2)

        // 레이아웃의 TextView와 연결
        bookRecord = view.findViewById<TextView>(R.id.bookRecord)
        myBookRecord1 = view.findViewById<TextView>(R.id.myBookRecord1)
        myBookRecord2 = view.findViewById<TextView>(R.id.myBookRecord2)
        myBookRecord3 = view.findViewById<TextView>(R.id.myBookRecord3)
        one = view.findViewById(R.id.one)
        two = view.findViewById(R.id.two)

        // DB 관련
        myHelper = DBManager(activity, "bookDB", null, 1)
      //  sqlDB = myHelper.writableDatabase
     //   sqlDB.execSQL("INSERT INTO bookDB VALUES ('RED', 'asd', 'wer', 'wsw', 1, 4, 7)")
        //sqlDB.close()


        // 레이아웃의 스위치와 연결
        switch1 = view.findViewById<Switch>(R.id.switch1)


        // 스위치가 on일 경우 성인 평균 데이터와 나의 독서량 비교 그래프 출력
        switch1.setOnClickListener {

            if (switch1.isChecked == true){
                bookRecord.visibility = View.VISIBLE
                barChart1.visibility = View.INVISIBLE
                barChart2.visibility = View.VISIBLE
                myBookRecord1.visibility = View.INVISIBLE
                myBookRecord2.visibility = View.INVISIBLE
                myBookRecord3.visibility = View.INVISIBLE
                one.visibility = View.INVISIBLE
                two.visibility = View.VISIBLE
                setWeek2(barChart2)

            } else {
                barChart2.visibility = View.INVISIBLE
                barChart1.visibility = View.VISIBLE
                bookRecord.visibility = View.INVISIBLE
                myBookRecord1.visibility = View.VISIBLE
                myBookRecord2.visibility = View.VISIBLE
                myBookRecord3.visibility = View.VISIBLE
                one.visibility = View.VISIBLE
                two.visibility = View.INVISIBLE
                setWeek(barChart1)
            }
        }

        // barChart 출력
        setWeek(barChart1)

        return view
    }




   /* private fun setChartView(view: View) {
        var chartWeek = view.findViewById<BarChart>(R.id.barChart)
        setWeek(chartWeek)
    }*/

    private fun initBarDataSet(barDataSet: BarDataSet) {

        // 막대 색상 변경
        barDataSet.color = Color.parseColor("#304567")

        // 범례에서 양식 크기 설정
        barDataSet.formSize = 15f

        // 막대의 값 표시, 설정되지 않은 경우 기본값
        barDataSet.setDrawValues(false)

        // 막대 값의 텍스트 크기 설정
        barDataSet.valueTextSize = 12f

    }

    // barChart 출력 메소드
    private fun setWeek(barChart: BarChart) {  // 이 함수 하나 더 만들어서 그래프 두개 구현해보기

        // barChart의 색상, 크기 지정
        initBarChart(barChart)

        // Zoom In/Out
        barChart.setScaleEnabled(false)

        // 그래프의 데이터 값이 들어갈 리스트
        val valueList = ArrayList<Int>()
        val entries: ArrayList<BarEntry> = ArrayList()
        val title = "이번달 독서 통계"



        sqlDB = myHelper.writableDatabase

        var cursor: Cursor

        // 현재 시간 가져와서 월 값 가져오기
        val now = System.currentTimeMillis()
        val nowDate = Date(now)

        // 날짜, 시간을 가져오고 싶은 형태로 선언
        val nowFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))

        // 현재 시간을 nowFormat에 선언한 형태의 String으로 변환 '2023-01-27 13:26:59 금'
        val  strNow = nowFormat.format(nowDate)

        // 월 정보만 추출
        val subMonth = strNow.substring(5 until 7)

        // 지난달 월 값 구하기
       var lastMonth = subMonth.toInt() - 1
        if (lastMonth == 0) {
            lastMonth = 12
        }

        // 지난달 = 이번달 - 1 해서 그래프 만들기

        // 평균 성인 독서량을 데이터 리스트에 입력
        cursor = sqlDB.rawQuery("SELECT readAvg FROM readingDB WHERE category = '소계';", null)
        var readAvg = ""
        while (cursor.moveToNext()){
            readAvg += cursor.getString(0)
        }
        valueList.add(readAvg.toInt())


        // 나의 독서량을 데이터 리스트에 입력
        cursor = sqlDB.rawQuery("SELECT * FROM bookDB;", null)
        var recordCounter = cursor.count.toString()
        myBookRecord1.text = "이번 달에 " + recordCounter + "권 읽었어요!"
        valueList.add(recordCounter.toInt())

        var comparAvg = readAvg.toInt() - recordCounter.toInt()
        if (comparAvg < 0) {
            bookRecord.text = "성인 평균 독서량보다 " + abs(comparAvg) + "권 더 읽었어요!"
        } else if (comparAvg == 0) {
            bookRecord.text = "성인 평균 독서량인 " + comparAvg + "권 읽었어요!"
        } else {
            bookRecord.text = "총 " + recordCounter + "권 읽었어요.\n" + (comparAvg + 1) + "권 더 읽으면 성인 평균 독서량 이상이에요!"
        }




        cursor.close()
        sqlDB.close()


        // 그래프에 데이터 입력 (리스트 사이즈만큼)
        for (i in 0 until valueList.size) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }

        // 그래프와 제목을 결합
        val barDataSet = BarDataSet(entries, title)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()

    }

    // barChart2 출력 메소드
    private fun setWeek2(barChart: BarChart) {  // 두번째 함수

        // barChart의 색상, 크기 지정
        initBarChart(barChart)

        // Zoom In/Out
        barChart.setScaleEnabled(false)

        // 그래프의 데이터 값이 들어갈 리스트
        val valueList = ArrayList<Int>()
        val entries: ArrayList<BarEntry> = ArrayList()
        val title = "나의 독서 통계"



        sqlDB = myHelper.readableDatabase
        var cursor: Cursor


        // 평균 성인 독서량을 데이터 리스트에 입력
        cursor = sqlDB.rawQuery("SELECT readAvg FROM readingDB WHERE category = '소계';", null)
        var readAvg = ""
        while (cursor.moveToNext()){
            readAvg += cursor.getString(0)
        }
    //    var TAG = "먼슬리"
    //    Log.d(TAG, "${readAvg}")
        valueList.add(readAvg.toInt())


        // 나의 독서량을 데이터 리스트에 입력
        cursor = sqlDB.rawQuery("SELECT * FROM bookDB;", null)
        var recordCounter = cursor.count.toString()
        myBookRecord1.text = "이번 달에 " + recordCounter + "권 읽었어요!"
        valueList.add(recordCounter.toInt())

        var comparAvg = readAvg.toInt() - recordCounter.toInt()
        if (comparAvg < 0) {
            bookRecord.text = "성인 평균 독서량보다 " + abs(comparAvg) + "권 더 읽었어요!"
        } else if (comparAvg == 0) {
            bookRecord.text = "성인 평균 독서량인 " + comparAvg + "권 읽었어요!"
        } else {
            bookRecord.text = "총 " + recordCounter + "권 읽었어요.\n" + (comparAvg + 1) + "권 더 읽으면 성인 평균 독서량 이상이에요!"
        }




        cursor.close()
        sqlDB.close()


        // 그래프에 데이터 입력 (리스트 사이즈만큼)
        for (i in 0 until valueList.size) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }

        // 그래프와 제목을 결합
        val barDataSet = BarDataSet(entries, title)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()

    }

    // barChart의 색상, 위치, 제목 설정 메소드
    private fun initBarChart(barChart: BarChart) {

        // 차트의 회색 배경 숨기기, 설정되지 않은 경우 기본 false
        barChart.setDrawGridBackground(false)

        // 막대 그림자 제거, 설정되지 않은 경우 기본 false
        barChart.setDrawBarShadow(false)

        // 차트의 테두리 제거, 설정되지 않은 경우 기본 false
        barChart.setDrawBorders(false)

        // 오른쪽 아래 모서리에 있는 설명 레이블 텍스트를 제거
        val description = Description()
        description.setEnabled(false)
        barChart.setDescription(description)

        // X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)


        // 바텀 좌표 값
        val xAxis: XAxis = barChart.getXAxis()

        // x축의 위치를 하단으로 변경
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        // 격자선의 수평 거리 설정 (하단 값)
        xAxis.granularity = 1f

        // 하단 값의 색상
        xAxis.textColor = Color.WHITE

        // x축 선 숨기기, 설정되지 않은 경우 기본 true
        xAxis.setDrawAxisLine(true)

        // 수직 그리드 선 숨기기, 설정되지 않은 경우 기본 true
        xAxis.setDrawGridLines(false)


        // 좌측 값 y축 선 숨기기, 설정되지 않은 경우 기본 true
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)

        // 좌측 값 색상 설정
        leftAxis.textColor = Color.BLACK


        //우측 값 y축 선 숨기기, 설정되지 않은 경우 기본 true
        val rightAxis: YAxis = barChart.getAxisRight()
        rightAxis.setDrawAxisLine(false)

        // 우측 값 색상 설정
        rightAxis.textColor = Color.WHITE


        // barChart의 타이틀
        val legend: Legend = barChart.getLegend()

        // 기준 양식의 모양을 선으로 설정, 기본 사각형 모양
        legend.form = Legend.LegendForm.LINE

        // 그래프 제목의 텍스트 크기, 색상 설정
        legend.textSize = 13f
        legend.textColor = Color.rgb(81, 99, 177)

        // 그래프 제목 정렬 설정
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        // 쌓기 방향 설정
        legend.orientation = Legend.LegendOrientation.HORIZONTAL

        // 차트 외부에 기준 위치 설정, 설정되지 않은 경우 기본 false
        legend.setDrawInside(false)
    }




    }
