package com.android.bookdiary

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter( private val dayList: ArrayList<String>,
            private  val onItemListener: OnitemListener):
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){

        val dayText : TextView = itemView.findViewById<TextView>(R.id.dayText)




    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_item, parent, false)

        return ItemViewHolder(view)
    }

    //데이터 설정
    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        var activity:MainActivity? =null
        var day = dayList[holder.adapterPosition] //날짜 변수에 담기
        holder.dayText.text = day


        if(position +1 % 7 ==0){ //토요일은 파랑, 일요일은 빨강
            holder.dayText.setTextColor(Color.BLUE)
        }else if(position == 0 || position % 7 == 0){
            holder.dayText.setTextColor(Color.RED)
        }

        holder.itemView.setOnClickListener {//날짜 클릭시 이벤트 발생
            //itemClickListener.onClick(it, position)
            onItemListener.onItemClick(day) // 인터페이스를 통해 날짜 넘겨줌




        }

    }




    override fun getItemCount(): Int {
        return dayList.size
    }

    interface ItemClickListener{

        fun onClick(view: View, position: Int)

    }

    private lateinit var itemClickListener:ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }



}