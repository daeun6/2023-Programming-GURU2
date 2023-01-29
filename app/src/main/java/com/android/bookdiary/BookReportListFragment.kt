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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 해당 책의 독후감 리스트를 보여주는 프래그먼트
class BookReportListFragment : Fragment(), BookReportListHandler {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var ivColor: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvAuthor: TextView
    lateinit var tvPage: TextView
    lateinit var btnUpdateAuthor: Button
    lateinit var btnUpdatePage: Button
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

        ivColor = view.findViewById(R.id.ivColor)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvAuthor = view.findViewById(R.id.tvAuthor)
        tvPage = view.findViewById(R.id.tvPage)
        btnUpdateAuthor = view.findViewById(R.id.btnUpdateAuthor)
        btnUpdatePage = view.findViewById(R.id.btnUpdatePage)
        btnDelete = view.findViewById(R.id.btnDelete)
        reportRecyclerView = view.findViewById(R.id.reportRecyclerView)

        if(arguments != null) {
            str_title = arguments?.getString("title").toString()    // AllFragment, EdFragment, IngFragment, WillFragment에서 전달한 책 제목 데이터 받기
        }

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE title = '" + str_title +"';", null)  // 전달받아 온 책 제목과 일치하는 데이터를 조회

        if (cursor.moveToNext()){   // 커서를 이용하여 조건에 맞는 모든 책의 저자, 총 페이지 수, 책 컬러 데이터를 가져오기
            str_author = cursor.getString(cursor.getColumnIndex("author")).toString()
            page = cursor.getInt(cursor.getColumnIndex("totalPage"))
            str_color = cursor.getString(cursor.getColumnIndex("color")).toString()
        }

        tvTitle.text = str_title
        tvAuthor.text = str_author
        tvPage.text = "" + page + "\n"

        for (i in 1..10) {  //DB 불러와서 전달값에 따라 이미지 뷰의 색을 변경
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
        }

        // 저자 수정 버튼
        btnUpdateAuthor.setOnClickListener {
            var title = str_title
            var author = str_author
            var page = page
            var color = str_color

            var bundle = Bundle()
            bundle.putString("title", title)
            bundle.putInt("page", page)
            bundle.putString("color", color)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var bookUpdateAuthorFragment = BookUpdateAuthorFragment()
            bookUpdateAuthorFragment.arguments = bundle
            ft.replace(R.id.container, bookUpdateAuthorFragment).commit()   // 책 제목, 저자, 총 페이지 수, 책 컬러를 전달하며 화면을 전환
        }

        // 페이지 수정 버튼
        btnUpdatePage.setOnClickListener {
            var title = str_title
            var author = str_author
            var page = page
            var color = str_color

            var bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("author", author)
            bundle.putString("color", color)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var bookUpdatePageFragment = BookUpdatePageFragment()
            bookUpdatePageFragment.arguments = bundle
            ft.replace(R.id.container, bookUpdatePageFragment).commit()
        }

        // 해당 책 삭제 버튼
        btnDelete.setOnClickListener {
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("DELETE FROM bookDB WHERE title = '" + str_title +"';")
            sqlitedb.execSQL("DELETE FROM writeDB WHERE dTitle = '" + str_title +"';")

            sqlitedb.close()

            val listFragment = ListFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, listFragment)
            transaction.commit()    // 삭제 후엔 책 리스트를 보여주는 프래그먼트로 화면이 전환
        }

        val bookReportListDataArray: ArrayList<BookReportListData> = ArrayList()
        lateinit var reportRecyclerView: RecyclerView

        cursor = sqlitedb.rawQuery("SELECT * FROM writeDB WHERE dTitle = '" + str_title +"';", null)    // 해당 책의 독후감 리스트를 조회

        while (cursor.moveToNext()) {
            var str_report = cursor.getString(cursor.getColumnIndex("dDate"))
            var data: BookReportListData = BookReportListData(str_report)
            bookReportListDataArray.add(data)
        }

        reportRecyclerView = view.findViewById(R.id.reportRecyclerView!!) as RecyclerView
        reportRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        reportRecyclerView.adapter = BookReportListAdapter(requireContext(), bookReportListDataArray, this)

        cursor.close()
        sqlitedb.close()
        dbManager.close()

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