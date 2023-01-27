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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookReportListFragment : Fragment(), BookReportListHandler {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var ivColor: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvAuthor: TextView
    lateinit var tvPage: TextView
    lateinit var btnUpdateAuthor: Button
    lateinit var btnUpdatePage: Button
    lateinit var btnUpdateColor: Button
    lateinit var btnDelete: Button
    lateinit var reportRecyclerView: RecyclerView

    lateinit var str_title: String
    lateinit var str_author: String
    var page: Int = 0
    lateinit var str_color: String

    @SuppressLint("Range", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_report_list, container, false)

//        var tvTitle: TextView
//        var tvAuthor: TextView
//        var tvPage: TextView
        ivColor = view.findViewById(R.id.ivColor)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvAuthor = view.findViewById(R.id.tvAuthor)
        tvPage = view.findViewById(R.id.tvPage)
        btnUpdateAuthor = view.findViewById(R.id.btnUpdateAuthor)
        btnUpdatePage = view.findViewById(R.id.btnUpdatePage)
        btnUpdateColor = view.findViewById(R.id.btnUpdateColor)
        btnDelete = view.findViewById(R.id.btnDelete)
        reportRecyclerView = view.findViewById(R.id.reportRecyclerView)

        if(arguments != null) {
            str_title = arguments?.getString("title").toString()
        }

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE title = '" + str_title +"';", null)

        if (cursor.moveToNext()){
            str_author = cursor.getString(cursor.getColumnIndex("author")).toString()
            page = cursor.getInt(cursor.getColumnIndex("totalPage"))
            str_color = cursor.getString(cursor.getColumnIndex("color")).toString()
        }


        tvTitle.text = str_title
        tvAuthor.text = str_author
        tvPage.text = "" + page + "\n"

        for (i in 1..10) { //DB 불러와서 전달값에 따라 바꿔야함
            if (str_color == "RED") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_red)
            }
            else if (str_color == "BLUE") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_blue)
            }
            else if (str_color == "GREEN") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_green)
            }
            else if (str_color == "ORANGE") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_orange)
            }
            else if (str_color == "YELLOW") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_yellow)
            }
            else if (str_color == "PURPLE") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_purple)
            }
            else if (str_color == "NAVY") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_navy)
            }
            else if (str_color == "PINK") {
                ivColor.setBackgroundResource(R.drawable.layer_button_checked_pink)
            }
            //bookTitleText.text = item.bookTitle.toString() // 책 제목 불러오기
        }


        btnUpdateAuthor.setOnClickListener {

            var title = str_title
            var author = str_author
            var page = page
            var color = str_color

            var bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("author", author)
            bundle.putInt("page", page)
            bundle.putString("color", color)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var bookUpdateAuthorFragment = BookUpdateAuthorFragment()
            bookUpdateAuthorFragment.arguments = bundle
            ft.replace(R.id.container, bookUpdateAuthorFragment).commit()
            Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
        }

        btnUpdatePage.setOnClickListener {

            var title = str_title
            var author = str_author
            var page = page
            var color = str_color

            var bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("author", author)
            bundle.putInt("page", page)
            bundle.putString("color", color)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var bookUpdatePageFragment = BookUpdatePageFragment()
            bookUpdatePageFragment.arguments = bundle
            ft.replace(R.id.container, bookUpdatePageFragment).commit()
            Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
        }

        btnUpdateColor.setOnClickListener {

            var title = str_title
            var author = str_author
            var page = page
            var color = str_color

            var bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("author", author)
            bundle.putInt("page", page)
            bundle.putString("color", color)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var bookUpdateColorFragment = BookUpdateColorFragment()
            bookUpdateColorFragment.arguments = bundle
            ft.replace(R.id.container, bookUpdateColorFragment).commit()
            Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
        }

        btnDelete.setOnClickListener {
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("DELETE FROM bookDB WHERE title = '" + str_title +"';")

            sqlitedb.close()

            Toast.makeText(context, "삭제됨", Toast.LENGTH_SHORT).show()

            val listFragment = ListFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, listFragment)
            transaction.commit()
        }

        val bookReportListDataArray: ArrayList<BookReportListData> = ArrayList()
        lateinit var reportRecyclerView: RecyclerView

        cursor = sqlitedb.rawQuery("SELECT * FROM writeDB WHERE dTitle = '" + str_title +"';", null)

        reportRecyclerView = view.findViewById(R.id.reportRecyclerView!!) as RecyclerView
        reportRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        reportRecyclerView.adapter = BookReportListAdapter(requireContext(), bookReportListDataArray, this)

        while (cursor.moveToNext()) {
            var str_report = cursor.getString(cursor.getColumnIndex("dDate"))

            //var percentage: Int = (now_page / total_page) * 100.toInt()
            //progressBar.progress = percentage

            var data: BookReportListData = BookReportListData(str_report)
            bookReportListDataArray.add(data)
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()


//        var num: Int = 0
//        while (cursor.moveToNext()) {
//            str_title = cursor.getString(cursor.getColumnIndex("title")).toString() //나중에 이 부분 날짜로 바꾸면 됨.
//            str_author = cursor.getString(cursor.getColumnIndex("author")).toString()
//            page = cursor.getInt(cursor.getColumnIndex("totalPage"))
//
//            var layout_item: LinearLayout = LinearLayout(activity)
//            layout_item.orientation = LinearLayout.VERTICAL
//            layout_item.setPadding(20, 10, 20, 10)
//            layout_item.id = num
//            layout_item.setTag(str_title)
//
//            var tvTitle: TextView = TextView(activity)
//            tvTitle.text = str_title
//            layout_item.addView(tvTitle)
//
//            var tvAuthor: TextView = TextView(activity)
//            tvAuthor.text = str_author
//            layout_item.addView(tvAuthor)
//
//            var tvPage: TextView = TextView(activity)
//            tvPage.text = page.toString()
//            layout_item.addView(tvPage)
//
//            layout_item.setOnClickListener {
//                //val DetailFragment = DetailFragment()
//                val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
//                //transaction.replace(R.id.container, DetailFragment)
//                transaction.commit()
//
//                reportLayout.addView(layout_item)
//                num++
//            }
//
//            tvTitle.text = str_title
//            tvAuthor.text = str_author
//            tvPage.text = "" + page + "\n"
//
//            cursor.close()
//            sqlitedb.close()
//            dbManager.close()
//        }

//        tvTitle.text = str_title
//        tvAuthor.text = str_author
//        tvPage.text = "" + page + "\n"
//
//        cursor.close()
//        sqlitedb.close()
//        dbManager.close()




        return view
    }

    override fun clickedBookReportList(book: BookReportListData) {
        var dDate = book.dDate
        var title = str_title
        var bundle = Bundle()
        bundle.putString("dDate", dDate)
        bundle.putString("title", title)
        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

        var detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        ft.replace(R.id.container, detailFragment).commit()
        Toast.makeText(activity, dDate, Toast.LENGTH_SHORT).show()
    }


}