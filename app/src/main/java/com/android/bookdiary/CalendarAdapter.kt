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


        fun bind(item: CalendarData) {
            val dayColor = item.color.toString()
            val dayDate = item.date.toString()


        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var TAG: String = " 캘린더어댑터"

        var num: Int = calendarDataArray.size
        Log.d(TAG, "$num")

        var day = dayList[holder.position] //날짜 변수에 담기

        var data: CalendarData
        var colorDate = arrayOfNulls<String>(num) // 날짜 받아옴(db에 저장된 날짜)
        var colorYear = arrayOfNulls<String>(num) // 날짜 중 년도
        var colorMonth = arrayOfNulls<String>(num) // 날짜 중 월
        var colorDay = arrayOfNulls<String>(num) // 날짜 중 일

        var selectedColor = arrayOfNulls<String>(num)



        for (i in 1..num - 1) {
            Log.d(TAG, "for문 들어옴 ")

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


        }


        if (day == null) {

            holder.dayText.text = ""

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.dayText.text = day.dayOfMonth.toString()
            }
            holder.dayImg.setImageResource(R.drawable.ic_baseline_menu_book_24)
            for (i in 1..num - 1) {

                if(colorDay[i]== day.dayOfMonth.toString() && colorMonth[i] == day.monthValue.toString() && colorYear[i] == day.year.toString()){
                    if(selectedColor[i] == "BLUE") {
                        holder.dayImg.setColorFilter(Color.parseColor("#9BF6FF"))
                            }
                    if(selectedColor[i] == "RED") {
                        holder.dayImg.setColorFilter(Color.parseColor("#FFADAD"))
                            }
                    if(selectedColor[i] == "PINK") {
                        holder.dayImg.setColorFilter(Color.parseColor("#FFADAD"))
                    }
                    if(selectedColor[i] == "GREEN") {
                        holder.dayImg.setColorFilter(Color.parseColor("#CAFFBF"))
                    }
                    if(selectedColor[i] == "ORANGE") {
                        holder.dayImg.setColorFilter(Color.parseColor("#FFADAD"))
                    }
                    if(selectedColor[i] == "YELLOW") {
                        holder.dayImg.setColorFilter(Color.parseColor("#FDFFB6"))
                    }
                    if(selectedColor[i] == "PURPLE") {
                        holder.dayImg.setColorFilter(Color.parseColor("#BDB2FF"))
                    }
                    if(selectedColor[i] == "NAVY") {
                        holder.dayImg.setColorFilter(Color.parseColor("#A0C4FF"))
                    }


                }


            }

            if (position + 1 % 7 == 0) { //토요일은 파랑, 일요일은 빨강
                holder.dayText.setTextColor(Color.BLUE)
            } else if (position == 0 || position % 7 == 0) {
                holder.dayText.setTextColor(Color.RED)
            }


            holder.itemView.setOnClickListener {//날짜 클릭시 이벤트 발생
                //itemClickListener.onClick(it, position)
                onItemListener.onItemClick(day) // 인터페이스를 통해 날짜 넘겨줌

                /*print(holder[position].toString())

             val curpos : Int = position // 아이템 번호 받아오기?..
             val days : String = dayList.get(curpos) // 저장할 번호 가져오기.... 어렵네
             이거 번호 넘겨주고
             아예 해당하는 아이템 객체를 색칠할 수 있는 어댑터를 따로 만들어볼까?
             괜찮은 생각인가..??? ㅜㅜㅜㅜㅜ

             인터페이스를 사용하지 않는 방법을 써서 여기서 코딩

 */


            }

        }


    }
}




