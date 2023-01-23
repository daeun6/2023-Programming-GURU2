package com.android.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter (private val context: Context, val noteDataArray: ArrayList<NoteData>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() { //2

    //var datas = mutableListOf<NoteData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = noteDataArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data: NoteData = noteDataArray[position]
        holder.title.text = data.title
        holder.nowPage.text = data.nowPage.toString()
        holder.totalPage.text = data.totalPage.toString()

//        if (holder.colorView.text == "red") {
//            //holder.layout.list_item_layout.setBackgroundColor(Color.parseColor("#aaaaaa"))
//            holder.layout.note_item.colorView
//        }
        holder.colorView.text = data.colorView


        /*holder.itemView.setOnClickListener {

        }*/

        //holder.bind(noteDataArray[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //private var colorView: ImageView = itemView.findViewById(R.id.colorView)
        val title: TextView = itemView.findViewById(R.id.title)
        //private var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val nowPage: TextView = itemView.findViewById(R.id.nowPage)
        val totalPage: TextView = itemView.findViewById(R.id.totalPage)
        val colorView: TextView = itemView.findViewById(R.id.colorView)

        fun bind(item: NoteData) {
            //colorView = item.colorView
            title.text = item.title
            //progressBar = item.progressBar
            nowPage.text = item.nowPage.toString()
            totalPage.text = item.totalPage.toString()
            colorView.text = item.colorView
            //Glide.with(itemView).load(item.img).into(imgProfile)

//            itemView.setOnClickListener {
//                Intent(context, ProfileDetailActivity::class.java).apply {
//                    putExtra("data", item)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run { context.startActivity(this) }
//            }

        }


    }
}