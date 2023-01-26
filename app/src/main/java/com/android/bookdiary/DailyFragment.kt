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
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "DailyFragment"

class DailyFragment : Fragment(), DailyClickHandler {
    var dailyChoiceList: ArrayList<DailyChoiceData> = ArrayList()
    lateinit var dailyRecycler: RecyclerView
    lateinit var dbManager : DBManager
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var bookColor : String
    lateinit var bookTitle : String
    lateinit var id : String
    var totalPage : Int = 0
    lateinit var date : String


    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        Log.d(TAG, "파일 찾는중")
        dbManager = DBManager(activity, "bookDB", null, 1)
        Log.d(TAG, "${dbManager.databaseName}")
        sqlitedb = dbManager.readableDatabase
        Log.d(TAG, "파일 찾았다2")

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB;", null)
        if (cursor != null){
            Log.d(TAG, "테이블 찾았다")
        }

        dailyRecycler = view.findViewById(R.id.dailyRecycler!!) as RecyclerView
        dailyRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        dailyRecycler.adapter = DailyChoiceAdapter(requireContext(), dailyChoiceList, this)

        while (cursor.moveToNext()){
            Log.d(TAG, "dailyFragment while문에 들어옴")
            bookColor = cursor.getString(cursor.getColumnIndex("color"))
            Log.d(TAG, "book color 정상")
            bookTitle = cursor.getString(cursor.getColumnIndex("title"))
            Log.d(TAG, "book title 정상")
            id = "aa" //user가 1명
            Log.d(TAG, "id 정상")
            totalPage = cursor.getInt(cursor.getColumnIndex("totalPage"))
            Log.d(TAG, "totalPage 정상")
            Log.d(TAG, "totalPage ${totalPage}")
            date = arguments?.getString("key").toString()
            Log.d(TAG, "date 정상")
            var data : DailyChoiceData = DailyChoiceData(bookColor, bookTitle, id, date, totalPage)
            dailyChoiceList.add(data)
        }
        Log.d(TAG, "dailyFragment while문에서 나옴")

        cursor.close()
        sqlitedb.close()
        dbManager.close()


        return view
    }

    override fun clickedBookItem(book: DailyChoiceData) {
        Log.d(TAG, "ClickedBookItem: ${book.bookTitle}")
        Log.d(TAG, "ClickedBookItem: ${book.bookColor}")

        var dTitle = book.bookTitle
        var dColor = book.bookColor
        var bundle = Bundle()
        bundle.putString("dTitle", dTitle)
        bundle.putString("dColor", dColor)
        bundle.putString("dUser", id)
        bundle.putInt("dTotalPage", totalPage)
        bundle.putString("dDate", date)
        Log.d(TAG, "ClickedBookItem: ${book.totalPage}")

        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        var dailyMemoFragment = DailyMemoFragment()
        dailyMemoFragment.arguments = bundle

        ft.replace(R.id.container, dailyMemoFragment).commit()
        Toast.makeText(activity, dTitle, Toast.LENGTH_SHORT).show()
    }

}