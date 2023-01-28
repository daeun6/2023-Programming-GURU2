package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
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
    lateinit var accumPageString : String
    var accumPage : Int = 0
    lateinit var date : String

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        dbManager = DBManager(activity, "bookDB", null, 1) //bookDB 데이터베이스 불러오기
        sqlitedb = dbManager.readableDatabase

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB;", null) //bookDB 테이블 정보 불러오기

        dailyRecycler = view.findViewById(R.id.dailyRecycler!!) as RecyclerView
        dailyRecycler.layoutManager = GridLayoutManager(requireContext(), 3) // 1행에 3열씩 보이도록 설정
        dailyRecycler.adapter = DailyChoiceAdapter(requireContext(), dailyChoiceList, this)


        if(cursor.count == 0){
            Log.d(TAG, "DB에 책이 없음")
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.daily_zero_dialog, null, false)
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("책을 불러올 수 없어요")
            val  mAlertDialog = mBuilder.show()
            val parent = mDialogView.parent as ViewGroup
            val btn = mDialogView.findViewById<Button>(R.id.checkBtn)
            btn.setOnClickListener {
                parent.removeView(mDialogView)
                mAlertDialog.dismiss()
                val mainFragment = MainFragment()
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.container, mainFragment)
                transaction.commit()
            }
        }

        Log.d(TAG, "while 전")

        while (cursor.moveToNext()) { // bookDB에 값이 있는 동안 책 정보 불러와서 화면에 띄우기
            Log.d(TAG, "while 안")
            totalPage = cursor.getInt(cursor.getColumnIndex("totalPage"))
            accumPageString = cursor.getString(cursor.getColumnIndex("accumPage"))
            if (accumPageString == "null") {
                accumPage = 0
            } else {
                accumPage = accumPageString.toInt()
            }

            if (totalPage != accumPage) { //책을 다 읽지 않았을 때만 띄우기

                bookColor = cursor.getString(cursor.getColumnIndex("color"))
                bookTitle = cursor.getString(cursor.getColumnIndex("title"))
                id = "aa" //user가 1명
                date = arguments?.getString("key").toString()
                // DailyMemoFragment에서 날짜 정보 받아오기
                if (date == "null") {
                    date = arguments?.getString("dDate").toString()
                }

                var data: DailyChoiceData =
                    DailyChoiceData(bookColor, bookTitle, id, date, totalPage, accumPage)
                dailyChoiceList.add(data)

            }
        }

        Log.d(TAG, "while 후")

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        return view
    }

    override fun clickedBookItem(book: DailyChoiceData) { //책 정보 클릭시 DailyMemoFragment로 정보 넘기기

        var dTitle = book.bookTitle
        var dColor = book.bookColor
        var dTotalPage : String = book.totalPage.toString()
        var dAccumPage : Int = book.accumPage

        var bundle = Bundle()
        bundle.putString("dTitle", dTitle)
        bundle.putString("dColor", dColor)
        bundle.putString("dUser", id)
        bundle.putString("dTotalPage", dTotalPage)
        bundle.putString("dDate", date)
        bundle.putInt("dAccumPage", dAccumPage)

        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        var dailyMemoFragment = DailyMemoFragment()
        dailyMemoFragment.arguments = bundle

        ft.replace(R.id.container, dailyMemoFragment).commit()
        Toast.makeText(activity, date, Toast.LENGTH_SHORT).show()
    }

}