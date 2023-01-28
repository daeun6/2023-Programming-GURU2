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
import android.widget.*
import androidx.fragment.app.FragmentTransaction

class BookUpdatePageFragment : Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvTitle: TextView
    lateinit var tvAuthor: TextView
    lateinit var edtPage: EditText
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
        val view = inflater.inflate(R.layout.fragment_book_update_page, container, false)

        tvTitle = view.findViewById(R.id.tvTitle)
        tvAuthor = view.findViewById(R.id.tvAuthor)
        edtPage = view.findViewById(R.id.edtPage)

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
            str_color = arguments?.getString("color").toString()
        }

        tvTitle.setText(str_title)
        tvAuthor.setText(str_author)
        if (str_color == "RED"){
            rb_red.setChecked(true)
        }
        if (str_color == "ORANGE"){
            rb_orange.setChecked(true)
        }
        if (str_color == "YELLOW"){
            rb_yellow.setChecked(true)
        }
        if (str_color == "GREEN"){
            rb_green.setChecked(true)
        }
        if (str_color == "BLUE"){
            rb_blue.setChecked(true)
        }
        if (str_color == "NAVY"){
            rb_navy.setChecked(true)
        }
        if (str_color == "PURPLE"){
            rb_purple.setChecked(true)
        }
        if (str_color == "PINK"){
            rb_pink.setChecked(true)
        }

        var cursor: Cursor
        var accumPage : Int = 0
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE title = '" + str_title +"';", null)

        if (cursor.moveToNext()){
            accumPage = cursor.getInt(cursor.getColumnIndex("accumPage"))
        }


        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener {



            var pageString = edtPage.text.toString()
            var pageInt : Int = 0
            if (pageString != "") {
                pageInt = pageString.toInt()
            }

            Log.d("Update", "pageInt : ${pageInt} accumPage ; ${accumPage}")


            if (pageString == "") {
                    val mDialogView = LayoutInflater.from(context).inflate(R.layout.daily_null_dialog, null, false)
                    val mBuilder = AlertDialog.Builder(context)
                        .setView(mDialogView)
                        .setTitle("완료할 수 없어요")
                    val  mAlertDialog = mBuilder.show()
                    val parent = mDialogView.parent as ViewGroup
                    val btn = mDialogView.findViewById<Button>(R.id.dialogBtn)
                    btn.setOnClickListener {
                        parent.removeView(mDialogView)
                        mAlertDialog.dismiss()
                    }
            }

            else if (pageInt < accumPage) {
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.daily_null_dialog, null, false)
                val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                    .setTitle("완료할 수 없어요")
                val  mAlertDialog = mBuilder.show()
                val parent = mDialogView.parent as ViewGroup
                val btn = mDialogView.findViewById<Button>(R.id.dialogBtn)
                btn.setOnClickListener {
                    parent.removeView(mDialogView)
                    mAlertDialog.dismiss()
                }
            }


            else {
            sqlitedb.execSQL("UPDATE bookDB SET totalPage = '" + edtPage.text + "'  WHERE title = '" + str_title + "';")

            sqlitedb.close()

            Toast.makeText(context, "수정됨", Toast.LENGTH_SHORT).show()

            var title = str_title
            var bundle = Bundle()
            bundle.putString("title", title)
            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var listFragment = ListFragment()
            listFragment.arguments = bundle
            ft.replace(R.id.container, listFragment).commit()

        }
        }

        return view
    }

}