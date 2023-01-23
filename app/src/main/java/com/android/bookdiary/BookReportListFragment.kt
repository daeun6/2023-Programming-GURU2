package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

class BookReportListFragment : Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var tvTitle: TextView
    lateinit var tvAuthor: TextView
    lateinit var tvPage: TextView
    lateinit var btnModify: Button
    lateinit var reportLayout: LinearLayout

    lateinit var str_title: String
    lateinit var str_author: String
    var page: Int = 0

    @SuppressLint("Range", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_reg, container, false)
//        tvTitle = view.findViewById(R.id.tvTitle)
//        tvAuthor = view.findViewById(R.id.tvAuthor)
//        tvPage = view.findViewById(R.id.tvPage)
//        btnModify = view.findViewById(R.id.btnModify)
//        reportLayout = view.findViewById(R.id.reportLayout)

        /*if(arguments != null) {
            str_title = arguments?.getString("title").toString()
        }*/

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE title = '" +str_title +"';", null)

        var num: Int = 0
        while (cursor.moveToNext()) {
//            str_title = cursor.getString(cursor.getColumnIndex("title")).toString() //나중에 이 부분 날짜로 바꾸면 됨.
            str_author = cursor.getString(cursor.getColumnIndex("author")).toString()
            page = cursor.getInt(cursor.getColumnIndex("page"))

            var layout_item: LinearLayout = LinearLayout(activity)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.setPadding(20, 10, 20, 10)
            layout_item.id = num
            layout_item.setTag(str_title)

            var tvTitle: TextView = TextView(activity)
            tvTitle.text = str_title
            layout_item.addView(tvTitle)

            var tvAuthor: TextView = TextView(activity)
            tvAuthor.text = str_author
            layout_item.addView(tvAuthor)

            var tvPage: TextView = TextView(activity)
            tvPage.text = page.toString()
            layout_item.addView(tvPage)

            layout_item.setOnClickListener {
                val DetailFragment = DetailFragment()
                val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.container, DetailFragment)
                transaction.commit()

                reportLayout.addView(layout_item)
                num++
            }

            cursor.close()
            sqlitedb.close()
            dbManager.close()
        }

        tvTitle.text = str_title
        tvAuthor.text = str_author
        tvPage.text = "" + page + "\n"

        cursor.close()
        sqlitedb.close()
        dbManager.close()




        return view
    }


}