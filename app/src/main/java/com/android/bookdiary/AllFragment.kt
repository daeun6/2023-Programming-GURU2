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

// 나의 책장에서 전체 책 리스트를 보여주는 프래그먼트
class AllFragment : Fragment(), BookListHandler {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var recyclerView: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)

        var bookListDataArray: ArrayList<BookListData> = ArrayList()

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB;", null)   // bookDB에서 전체 데이터를 조회

        while (cursor.moveToNext()) {
            var str_title = cursor.getString(cursor.getColumnIndex("title"))    // 책 제목
            var total_page = cursor.getInt(cursor.getColumnIndex("totalPage"))  // 책의 전체 페이지
            var str_color = cursor.getString(cursor.getColumnIndex("color"))    // 책의 컬러
            var accum_page = cursor.getInt(cursor.getColumnIndex("accumPage"))  // 현재까지 읽은 총 페이지 수
            var percent = accum_page.toFloat() / total_page.toFloat() * 100 // 해당 책의 독서 진행도


            var data: BookListData = BookListData(str_title, accum_page, total_page, str_color, percent)
            bookListDataArray.add(data) // 리사이클러뷰에 반영할 데이터
        }

        recyclerView = view.findViewById(R.id.recyclerView!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = BookListAdapter(requireContext(), bookListDataArray, this)

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        return view
    }

    // BookListHandler 인터페이스의 메소드 오버라이딩 - 책장의 책 아이템을 클릭하면 해당 책의 독후감 리스트 프래그먼트(BookReportFragment)로 전환
    override fun clickedBookList(book: BookListData) {
        var title = book.title
        var bundle = Bundle()
        bundle.putString("title", title)    // 책 제목 전달
        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

        var bookReportListFragment = BookReportListFragment()
        bookReportListFragment.arguments = bundle
        ft.replace(R.id.container, bookReportListFragment).commit()
        Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
    }

}