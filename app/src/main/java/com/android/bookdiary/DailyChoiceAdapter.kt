package com.android.bookdiary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.daily_choice_list.view.*
import kotlinx.android.synthetic.main.fragment_daily.view.*
import kotlinx.android.synthetic.main.note_item.view.*


class DailyChoiceAdapter (private val context: Context, val dailyChoiceArray: ArrayList<DailyChoiceData>, private  val clickHandler: DailyClickHandler) :
    RecyclerView.Adapter<DailyChoiceAdapter.ViewHolder>() {


    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.daily_choice_list, parent, false)
        return ViewHolder(view)
    }

    // 리스트 내 아이템 개수
    override fun getItemCount(): Int = dailyChoiceArray.size

    //View에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data : DailyChoiceData = dailyChoiceArray[position]
        holder.bind(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var bookColorView: ImageView = itemView.findViewById(R.id.bookColorView)
        private var bookTitleText: TextView = itemView.findViewById(R.id.bookTitleText)
        private var dUser : TextView = itemView.findViewById(R.id.userText)
        private var dTotalPage : TextView = itemView.findViewById(R.id.totalPageText)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: DailyChoiceData) { //책 이미지 색상 정보 수정
                if (item.bookColor.toString() == "RED") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_red)
                }
                else if (item.bookColor.toString() == "BLUE") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_blue)
                }
                else if (item.bookColor.toString() == "GREEN") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_green)
                }
                else if (item.bookColor.toString() == "ORANGE") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_orange)
                }
                else if (item.bookColor.toString() == "YELLOW") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_yellow)
                }
                else if (item.bookColor.toString() == "PURPLE") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_purple)
                }
                else if (item.bookColor.toString() == "NAVY") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_navy)
                }
                else if (item.bookColor.toString() == "PINK") {
                    bookColorView.setBackgroundResource(R.drawable.layer_button_checked_pink)
                }
                bookTitleText.text = item.bookTitle.toString() // 책 제목 불러오기

                dUser.text = "aa"
                dTotalPage.text = item.totalPage.toString()

        }

        override fun onClick(v: View?) { //책 선택
            val position = dailyChoiceArray[position]
            clickHandler.clickedBookItem(position)
        }
    }
}



