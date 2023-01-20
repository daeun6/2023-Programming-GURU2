package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_item.view.*


class DailyChoiceAdapter (private val context: Context, val dailyChoiceArray: ArrayList<DailyChoiceData>) : RecyclerView.Adapter<DailyChoiceAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.daily_choice_list, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = dailyChoiceArray.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(dailyChoiceArray[position])
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private var bookColorBtn: ImageButton = itemView.findViewById(R.id.bookColorBtn)
            private var bookTitleText: TextView = itemView.findViewById(R.id.bookTitleText)

            fun bind(item: DailyChoiceData) {
                //bookColorBtn = item.bookColorBtn
                bookTitleText.text = item.bookTitleText.toString()
            }
        }
    }

