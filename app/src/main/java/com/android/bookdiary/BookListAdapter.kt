/*
어시슈트 - 소북소북 코드입니다.

정보보호학과 2020111323 김지원
정보보호학과 2021111325 김해린
정보보호학과 2021111336 송다은(팀 대표)
정보보호학과 2021111694 이가연

 */

package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

// 책장에서 책의 제목, 책의 색상, 현재까지 읽은 페이지 수, 전체 페이지 수, 진행률이 담긴 아이템뷰를 책의 개수만큼 보여주는 어댑터
class BookListAdapter(private val context: Context, val bookListDataArray: ArrayList<BookListData>, private val clickBookHandler: BookListHandler) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    // 뷰 홀더 클래스 객체를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent,false)
        return ViewHolder(view)
    }

    // 어댑터에 설정된 아이템 리스트 (bookListDataArray)의 크기를 반환하여 목록에 보여줄 아이템의 개수를 설정
    override fun getItemCount(): Int = bookListDataArray.size

    // 어댑터 생성자에 넘어온 데이터를 뷰 홀더에 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data: BookListData = bookListDataArray[position]
        holder.bind(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var title: TextView = itemView.findViewById(R.id.title)
        private var accumPage: TextView = itemView.findViewById(R.id.accumPage)
        private var totalPage: TextView = itemView.findViewById(R.id.totalPage)
        private var colorView: ImageView = itemView.findViewById(R.id.colorView)
        private var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: BookListData) {
            title.text = item.title
            accumPage.text = item.accumPage.toString()
            totalPage.text = item.totalPage.toString()
            progressBar.setProgress(item.progressBar.toInt(), false)

            for (i in 1..10) {
                if (item.colorView.toString() == "RED") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_red)
                }
                else if (item.colorView.toString() == "BLUE") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_blue)
                }
                else if (item.colorView.toString() == "GREEN") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_green)
                }
                else if (item.colorView.toString() == "ORANGE") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_orange)
                }
                else if (item.colorView.toString() == "YELLOW") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_yellow)
                }
                else if (item.colorView.toString() == "PURPLE") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_purple)
                }
                else if (item.colorView.toString() == "NAVY") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_navy)
                }
                else if (item.colorView.toString() == "PINK") {
                    colorView.setBackgroundResource(R.drawable.layer_button_checked_pink)
                }

            }

        }

        override fun onClick(v: View?) {
            val position = bookListDataArray[position]
            clickBookHandler.clickedBookList(position)
        }

    }
}