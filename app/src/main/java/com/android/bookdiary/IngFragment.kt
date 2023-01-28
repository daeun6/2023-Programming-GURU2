/*
어시슈트 - 소북소북 코드입니다.

정보보호학과 2020111323 김지원
정보보호학과 2021111325 김해린
정보보호학과 2021111336 송다은(팀 대표)
정보보호학과 2021111694 이가연

 */

package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IngFragment : Fragment(), BookListHandler {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var recyclerView: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ing, container, false)

        var bookListDataArray: ArrayList<BookListData> = ArrayList()

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE totalPage <> accumPage and accumPage <> 0;", null)

        recyclerView = view.findViewById(R.id.recyclerView!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = BookListAdapter(requireContext(), bookListDataArray, this)

        while (cursor.moveToNext()) {
            var str_title = cursor.getString(cursor.getColumnIndex("title"))
            var now_page = cursor.getInt(cursor.getColumnIndex("nowPage"))
            var total_page = cursor.getInt(cursor.getColumnIndex("totalPage"))
            var str_color = cursor.getString(cursor.getColumnIndex("color"))
            var accum_page = cursor.getInt(cursor.getColumnIndex("accumPage"))
            var percent = accum_page.toFloat() / total_page.toFloat() * 100
            var data: BookListData = BookListData(str_title, accum_page, total_page, str_color, percent)
            bookListDataArray.add(data)
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        return view

    }

    override fun clickedBookList(book: BookListData) {
        var title = book.title
        var bundle = Bundle()
        bundle.putString("title", title)
        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

        var bookReportListFragment = BookReportListFragment()
        bookReportListFragment.arguments = bundle
        ft.replace(R.id.container, bookReportListFragment).commit()
        Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
    }

}