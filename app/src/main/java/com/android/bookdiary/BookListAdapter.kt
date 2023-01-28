package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class BookListAdapter(private val context: Context, val bookListDataArray: ArrayList<BookListData>, private val clickBookHandler: BookListHandler) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bookListDataArray.size

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