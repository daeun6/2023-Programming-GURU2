package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction

class BookUpdateColorFragment : Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvTitle: TextView
    lateinit var tvAuthor: TextView
    lateinit var tvPage: TextView
    lateinit var btnUpdate: Button

    lateinit var rg_Color: RadioGroup
    lateinit var rb_red: RadioButton
    lateinit var rb_orange: RadioButton
    lateinit var rb_yellow: RadioButton
    lateinit var rb_green: RadioButton
    lateinit var rb_blue: RadioButton
    lateinit var rb_navy: RadioButton
    lateinit var rb_purple: RadioButton
    lateinit var rb_pink: RadioButton

    lateinit var str_title: String
    lateinit var str_author: String
    lateinit var page: String
    lateinit var str_color: String

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_update_color, container, false)

        tvTitle = view.findViewById(R.id.tvTitle)
        tvAuthor = view.findViewById(R.id.tvAuthor)
        tvPage = view.findViewById(R.id.tvPage)

        rg_Color = view.findViewById(R.id.radioGroup)
        rb_red = view.findViewById(R.id.rbRed)
        rb_orange = view.findViewById(R.id.rbOrange)
        rb_yellow = view.findViewById(R.id.rbYellow)
        rb_green = view.findViewById(R.id.rbGreen)
        rb_blue = view.findViewById(R.id.rbBlue)
        rb_navy = view.findViewById(R.id.rbNavy)
        rb_purple = view.findViewById(R.id.rbPurple)
        rb_pink = view.findViewById(R.id.rbPink)

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.writableDatabase

        if(arguments != null) {
            str_title = arguments?.getString("title").toString()
            str_author = arguments?.getString("author").toString()
            page = arguments?.getInt("page").toString()
            str_color = arguments?.getString("color").toString()
        }

        tvTitle.setText(str_title)
        tvAuthor.setText(str_author)
        tvPage.setText(page)


        if(rg_Color.checkedRadioButtonId == R.id.rbRed) {
            str_color = "RED"
            Toast.makeText(context, "빨간색 선택됨", Toast.LENGTH_SHORT).show()
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbOrange) {
            str_color = "ORANGE"
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbYellow) {
            str_color = "YELLOW"
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbGreen) {
            str_color = "GREEN"
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbBlue) {
            str_color = "BLUE"
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbNavy) {
            str_color = "NAVY"
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbPurple) {
            str_color = "PURPLE"
        }

        if(rg_Color.checkedRadioButtonId == R.id.rbPink) {
            str_color = "PINK"
        }


        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE title = '" + str_title +"';", null)


        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener {

            sqlitedb.execSQL("UPDATE bookDB SET color = '"+ str_color + "'  WHERE title = '" + str_title + "';")

            sqlitedb.close()

            Toast.makeText(context, "수정됨", Toast.LENGTH_SHORT).show()

            var title = str_title
            var bundle = Bundle()
            bundle.putString("title", title)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var listFragment = ListFragment()
            listFragment.arguments = bundle
            ft.replace(R.id.container, listFragment).commit()
        }

        return view
    }

}