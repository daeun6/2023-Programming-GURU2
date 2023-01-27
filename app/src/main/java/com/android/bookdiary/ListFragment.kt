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
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), BookListHandler  { //1

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var btnAdd: Button
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    val bookListDataArray: ArrayList<BookListData> = ArrayList()
//    lateinit var recyclerView: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        btnAdd = view.findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val bookRegFragment = BookRegFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, bookRegFragment)
            transaction.commit()
        }

//        dbManager = DBManager(activity, "bookDB", null, 1)
//        sqlitedb = dbManager.readableDatabase
//
//        var cursor: Cursor
//        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB;", null)
//
//        recyclerView = view.findViewById(R.id.recyclerView!!) as RecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = BookListAdapter(requireContext(), bookListDataArray, this)
//
//        while (cursor.moveToNext()) {
//            var str_title = cursor.getString(cursor.getColumnIndex("title"))
//            var now_page = cursor.getInt(cursor.getColumnIndex("nowPage"))
//            var total_page = cursor.getInt(cursor.getColumnIndex("totalPage"))
//            var str_color = cursor.getString(cursor.getColumnIndex("color"))
//            var accum_page = cursor.getInt(cursor.getColumnIndex("accumPage"))
//
//            var percent = accum_page / total_page * 100
//
//            //var percentage: Int = (now_page / total_page) * 100.toInt()
//            //progressBar.progress = percentage
//
//            var data: BookListData = BookListData(str_title, accum_page, total_page, str_color, percent)
//            bookListDataArray.add(data)
//        }
//
//        cursor.close()
//        sqlitedb.close()
//        dbManager.close()



        val adapter = ViewPagerAdapter(activity?.supportFragmentManager!!)
        adapter.addFragment(AllFragment(), "전체")
        adapter.addFragment(EdFragment(), "-ed")
        adapter.addFragment(IngFragment(), "-ing")
        adapter.addFragment(WillFragment(), "will")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)


        return view

        //val viewPager : ViewPager = view.findViewById(R.id.viewPager)

        //val viewPagerFragmentAdapter = ViewPagerAdapter(childFragmentManager)
        //viewPager.adapter = viewPagerFragmentAdapter

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