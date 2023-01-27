package com.android.bookdiary

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarAdapter(private val dayList: ArrayList<LocalDate?>,
                      private val onItemListener: OnitemListener,
                      val calendarDataArray: ArrayList<CalendarData> ):
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {


    class ItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        operator fun get(position: Int) {

        }

    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_item, parent, false)

        return ViewHolder(view)
    }

    //데이터 설정


    override fun getItemCount(): Int {
        return dayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val dayText: TextView = itemView.findViewById<TextView>(R.id.dayText)
        val dayImg: ImageView = itemView.findViewById(R.id.dayImg)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var TAG: String = " 캘린더어댑터"

        var num: Int = calendarDataArray.size


        var day = dayList[holder.position] //날짜 변수에 담기

        var data: CalendarData
        var colorDate = arrayOfNulls<String>(num) // 날짜 받아옴(db에 저장된 날짜)
        var colorYear = arrayOfNulls<String>(num) // 날짜 중 년도
        var colorMonth = arrayOfNulls<String>(num) // 날짜 중 월
        var colorDay = arrayOfNulls<String>(num) // 날짜 중 일

        var selectedColor = arrayOfNulls<String>(num) // 해당 날짜의 색깔

        var colorRatio = arrayOfNulls<Int>(num)



        for (i in 1..num - 1) {

            var num2: Int = 0
            data = calendarDataArray[i]
            //Log.d(TAG, "$data")

            colorDate[i] = data.date.toString() // 2023년 01월 11일
            colorYear[i] = colorDate[i]?.substring(0 until 4) // 2023
            colorMonth[i] = colorDate[i]?.substring(6 until 8) // 01
            if(colorMonth[i] != "10" && colorMonth[i] != "11" && colorMonth[i] != "12") { // 10이하면 01, 02 이므로 숫자를 자름
                colorMonth[i] = colorMonth[i]?.substring(1 until 2)
            }
            colorDay[i] = colorDate[i]?.substring(10 until 12) // 11
            var intColorDay : Int = colorDay[i]!!.toInt()
            if(intColorDay < 10) {
                colorDay[i] = colorDay[i]?.substring(1 until 2)
            }

            selectedColor[i] = data.color.toString()
            colorRatio[i] = data.ratioPage


        }//


        if (day == null) {

            holder.dayText.text = ""

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.dayText.text = day.dayOfMonth.toString()
            }
            holder.dayImg.setImageResource(R.drawable.ic_round_menu_book_24)
            for (i in 1..num - 1) {

                var ratio : Int? = colorRatio[i]
                Log.d(TAG, "$ratio")


                if(colorDay[i]== day.dayOfMonth.toString() && colorMonth[i] == day.monthValue.toString() && colorYear[i] == day.year.toString()){
                    if(selectedColor[i] == "NAVY") {
                        if(colorRatio[i]!! >= 50){
                            holder.dayImg.setColorFilter(Color.parseColor("#A0C4FF"))
                        } else {
                            holder.dayImg.setColorFilter(Color.parseColor("#A0C4FF"))
                            holder.dayImg.setAlpha(0.5f)
                        }
                    }
                    if(selectedColor[i] == "RED") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#FFADAD"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#FFADAD"))

                        }
                    }
                    if(selectedColor[i] == "PINK") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#FFC6FF"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#FFC6FF"))

                        }
                    }
                    if(selectedColor[i] == "GREEN") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#CAFFBF"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#CAFFBF"))

                        }
                    }
                    if(selectedColor[i] == "ORANGE") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#FFD6A5"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#FFD6A5"))

                        }
                    }
                    if(selectedColor[i] == "YELLOW") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#FDFFB6"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#FDFFB6"))

                        }
                    }
                    if(selectedColor[i] == "PURPLE") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#BDB2FF"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#BDB2FF"))

                        }
                    }
                    if(selectedColor[i] == "BLUE") {
                        if (colorRatio[i]!! >= 50) {
                            holder.dayImg.setColorFilter(Color.parseColor("#9BF6FF"))
                        }else{
                            holder.dayImg.setAlpha(0.5f)
                            holder.dayImg.setColorFilter(Color.parseColor("#9BF6FF"))

                        }


                    }


                }


            }

            if (position + 1 % 7 == 0) { //토요일은 파랑, 일요일은 빨강
                holder.dayText.setTextColor(Color.BLUE)
            } else if (position == 0 || position % 7 == 0) {
                holder.dayText.setTextColor(Color.RED)
            }


            holder.itemView.setOnClickListener {//날짜 클릭시 이벤트 발생
                onItemListener.onItemClick(day) // 인터페이스를 통해 날짜 넘겨줌


            }

        }


    }
}




