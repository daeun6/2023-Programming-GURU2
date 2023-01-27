package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class BookReportListAdapter(private val context: Context, val bookReportListDataArray: ArrayList<BookReportListData>, private val clickBookReportHandler: BookReportListHandler) :
    RecyclerView.Adapter<BookReportListAdapter.ViewHolder>() { //2

    //var datas = mutableListOf<NoteData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bookReportListDataArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data: BookReportListData = bookReportListDataArray[position]
        holder.bind(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

//        private var title: TextView = itemView.findViewById(R.id.title)
//        private var accumPage: TextView = itemView.findViewById(R.id.accumPage)
//        private var totalPage: TextView = itemView.findViewById(R.id.totalPage)
//        private var colorView: ImageView = itemView.findViewById(R.id.colorView)
//        private var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private var report: TextView = itemView.findViewById(R.id.report)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: BookReportListData) {
            //colorView = item.colorView
//            title.text = item.title
//            //progressBar = item.progressBar
//            accumPage.text = item.accumPage.toString()
//            totalPage.text = item.totalPage.toString()
//            //colorView.text = item.colorView
//            //Glide.with(itemView).load(item.img).into(imgProfile)]
//            progressBar.setProgress(item.progressBar, true)
            report.text = item.report

//            for (i in 1..10) { //DB 불러와서 전달값에 따라 바꿔야함
//                if (item.colorView.toString() == "RED") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_red)
//                }
//                else if (item.colorView.toString() == "BLUE") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_blue)
//                }
//                else if (item.colorView.toString() == "GREEN") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_green)
//                }
//                else if (item.colorView.toString() == "ORANGE") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_orange)
//                }
//                else if (item.colorView.toString() == "YELLOW") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_yellow)
//                }
//                else if (item.colorView.toString() == "PURPLE") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_purple)
//                }
//                else if (item.colorView.toString() == "NAVY") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_navy)
//                }
//                else if (item.colorView.toString() == "PINK") {
//                    colorView.setBackgroundResource(R.drawable.layer_button_checked_pink)
//                }
                //bookTitleText.text = item.bookTitle.toString() // 책 제목 불러오기
            }

//            itemView.setOnClickListener {
//                Intent(context, ProfileDetailActivity::class.java).apply {
//                    putExtra("data", item)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run { context.startActivity(this) }
//                Toast.makeText(context, "ㅎㅇ", Toast.LENGTH_SHORT).show()
//            }

        override fun onClick(v: View?) {
            val position = bookReportListDataArray[position]
            clickBookReportHandler.clickedBookReportList(position)
        }


    }
}