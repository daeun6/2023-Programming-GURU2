package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class DetailFragment : Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase


    lateinit var btnModify: Button
    lateinit var btnDone: Button

    var str_date: String = ""
    var str_title: String =""
    var nowPage: Int = 0
    var str_sentence: String =""
    var str_think: String =""

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        var textViewNumber: TextView
        var likeSentence: TextView
        var myThink: TextView


        textViewNumber = view.findViewById(R.id.textViewNumber)
        Log.d("DetailFragment", "${textViewNumber}")
        likeSentence = view.findViewById(R.id.likeSentence)
        myThink = view.findViewById(R.id.myThink)
        btnModify = view.findViewById(R.id.btnModify)
        btnDone = view.findViewById(R.id.btnDone)

        if(arguments != null) {
            str_date = arguments?.getString("dDate").toString()
            str_title = arguments?.getString("title").toString()
        }

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.writableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM writeDB WHERE dTitle = '" + str_title +"';", null)

        if (cursor.moveToNext()){
            str_sentence = cursor.getString(cursor.getColumnIndex("dSentence")).toString()
            nowPage = cursor.getInt(cursor.getColumnIndex("dNowPage"))
            str_think = cursor.getString(cursor.getColumnIndex("dThink")).toString()
        }

//        likeSentence.setText(str_sentence)
//        textViewNumber.setText(nowPage)
//        myThink.setText(str_think)

        likeSentence.text = str_title
        textViewNumber.text = "" + nowPage
        myThink.text = str_think + "\n"


        btnModify.setOnClickListener {
            var title = str_title
            var dDate = str_date
            var bundle = Bundle()
            bundle.putString("dTitle", title)
            bundle.putString("dDate", dDate)

            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var detailModifyFragment = DetailModifyFragment()
            detailModifyFragment.arguments = bundle
            ft.replace(R.id.container, detailModifyFragment).commit()
        }

        btnDone.setOnClickListener {
            var title = str_title
            var bundle = Bundle()
            bundle.putString("title", title)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var bookReportListFragment = BookReportListFragment()
            bookReportListFragment.arguments = bundle
            ft.replace(R.id.container, bookReportListFragment).commit()
        }

        return view
    }



}