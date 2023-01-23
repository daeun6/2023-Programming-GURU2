package com.android.bookdiary

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter( private val dayList: ArrayList<String>,
            private  val onItemListener: OnitemListener,
            val calendarDataArray : ArrayList<CalendarData> ):
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {


    class ItemViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val dayText : TextView = itemView.findViewById<TextView>(R.id.dayText)
        val dayImg : ImageView = itemView.findViewById(R.id.dayImg)


        fun bind(item : CalendarData){
            val dayColor = item.color.toString()
            val dayDate = item.date.toString()


        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var TAG : String = " 캘린더어댑터"

        var num : Int = calendarDataArray.size
        Log.d(TAG,"$num")

        var day = dayList[holder.position] //날짜 변수에 담기

        var data :CalendarData
        var colorDay = arrayOfNulls<String>(num)
        //var selectedColor = data.color.toString()



        for(i in 1..num-1) {
            Log.d(TAG,"for문 들어옴 ")

            var num2: Int = 0
            data  = calendarDataArray[i]
            //Log.d(TAG, "$data")

            colorDay[i] = data.date.toString()
            var data2 = colorDay[i]
            Log.d(TAG, "$data2")

           // var qustn = colorDay[num2]

            //Log.d(TAG,"$qustn")

        }


        if(day == "") {

            holder.dayText.text = ""

        }else{
            holder.dayText.text = day
            holder.dayImg.setImageResource(R.drawable.ic_baseline_menu_book_24)
            for(i in 1.. num-1) {
                if (day == colorDay[i]) {
                    holder.dayImg.setBackgroundColor(Color.BLUE)

                }
            }

        }

        if(position +1 % 7 ==0){ //토요일은 파랑, 일요일은 빨강
            holder.dayText.setTextColor(Color.BLUE)
        }else if(position == 0 || position % 7 == 0){
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

 */


        }

    }


}


