package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.daily_choice_list.view.*
import kotlinx.android.synthetic.main.fragment_daily.view.*


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

        //holder.bookColorBtn = data.bookColor
        //holder.bookTitleText.text = data.bookTitle

        //holder.bind(data)

        holder.bind(data)


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var bookColorBtn: ImageButton = itemView.findViewById(R.id.bookColorBtn)
        private var bookTitleText: TextView = itemView.findViewById(R.id.bookTitleText)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: DailyChoiceData) {
            for (i in 1..10) { //DB 불러와서 전달값에 따라 바꿔야함
                if (item.bookColor.toString() == "red") {
                    bookColorBtn.setImageResource(R.drawable.ic_baseline_1k_24)
                }
                else if (item.bookColor.toString() == "blue") {
                    bookColorBtn.setImageResource(R.drawable.ic_baseline_2k_24)
                }
                else if (item.bookColor.toString() == "green") {
                    bookColorBtn.setImageResource(R.drawable.ic_baseline_3k_24)
                }

                bookTitleText.text = item.bookTitle.toString() // 책 제목 불러오기
            }
        }

        override fun onClick(v: View?) {
            val position = dailyChoiceArray[position]
            clickHandler.clickedBookItem(position)
        }
    }
}



