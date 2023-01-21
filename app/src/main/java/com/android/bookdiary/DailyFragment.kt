package com.android.bookdiary

import DBManager
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
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "DailyFragment"

class DailyFragment : Fragment(), DailyClickHandler {
    var dailyChoiceList: ArrayList<DailyChoiceData> = ArrayList()
    lateinit var dailyRecycler: RecyclerView
    lateinit var dbManager : DBManager
    lateinit var sqlitedb : SQLiteDatabase

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_daily, container, false)
//        Log.d(TAG, "파일 찾는중")
//        dbManager = DBManager(activity, "bookDB", null, 1)
//        Log.d(TAG, "${dbManager.databaseName}")
//        sqlitedb = dbManager.readableDatabase
//        Log.d(TAG, "파일 찾았다2")
//
//        var cursor : Cursor
//        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB;", null)
//        if (cursor != null){
//            Log.d(TAG, "테이블 못찾았다")
//        }
        dailyRecycler = view.findViewById(R.id.dailyRecycler!!) as RecyclerView
        dailyRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        dailyRecycler.adapter = DailyChoiceAdapter(requireContext(), dailyChoiceList, this)
//
//        dailyChoiceList.add(DailyChoiceData("1","호랑이"))
//        while (cursor.moveToNext()){
//            Log.d(TAG, "while문에 들어옴")
//            var bookColor = cursor.getString(cursor.getColumnIndex("color"))
//            var bookTitle = cursor.getString(cursor.getColumnIndex("title"))
//            var data : DailyChoiceData = DailyChoiceData(bookColor, bookTitle)
//            dailyChoiceList.add(data)
//            dailyChoiceList.add(DailyChoiceData("2","호시"))
//        }
//        dailyChoiceList.add(DailyChoiceData("3","파리"))
//
//        cursor.close()
//        sqlitedb.close()
//        dbManager.close()

//
        dailyChoiceList.add(DailyChoiceData("1","호랑이"))
        dailyChoiceList.add(DailyChoiceData("2", "호시"))
        dailyChoiceList.add(DailyChoiceData("3", "파리"))
        dailyChoiceList.add(DailyChoiceData("4","에펠탑"))
        dailyChoiceList.add(DailyChoiceData("5","여행"))
        dailyChoiceList.add(DailyChoiceData("6", "부러워"))
        dailyChoiceList.add(DailyChoiceData("7","호랑해"))
        dailyChoiceList.add(DailyChoiceData("8","햄스터"))
        dailyChoiceList.add(DailyChoiceData("9","햄랑해"))
        dailyChoiceList.add(DailyChoiceData("10","햄랑이"))



        val dailyCancleBtn = view.findViewById<Button>(R.id.dailyCancleBtn)
        dailyCancleBtn.setOnClickListener{
            val mainFragment = MainFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, mainFragment)
            transaction.commit()
        }

        val dailyNextBtn = view.findViewById<Button>(R.id.dailyNextBtn)
        dailyNextBtn.setOnClickListener{
            val dailyMemoFragment = DailyMemoFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, dailyMemoFragment)
            transaction.commit()
        }

        return view
    }

    override fun clickedBookItem(book: DailyChoiceData) {
        Log.d(TAG, "ClickedBookItem: ${book.bookTitle}")
    }

}