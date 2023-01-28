package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class BookReportListAdapter(private val context: Context, val bookReportListDataArray: ArrayList<BookReportListData>, private val clickBookReportHandler: BookReportListHandler) :
    RecyclerView.Adapter<BookReportListAdapter.ViewHolder>() {

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

        private var dDate: TextView = itemView.findViewById(R.id.dDate)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: BookReportListData) {

            dDate.text = item.dDate
            }


        override fun onClick(v: View?) {
            val position = bookReportListDataArray[position]
            clickBookReportHandler.clickedBookReportList(position)
        }


    }
}