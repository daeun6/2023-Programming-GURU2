/*
어시슈트 - 소북소북 코드입니다.

정보보호학과 2020111323 김지원
정보보호학과 2021111325 김해린
정보보호학과 2021111336 송다은(팀 대표)
정보보호학과 2021111694 이가연

 */

package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction

import kotlinx.android.synthetic.main.fragment_book_reg.*

class BookRegFragment : Fragment() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var btnRegister: Button
    lateinit var btnColor: Button
    lateinit var edtTitle: EditText
    lateinit var edtAuthor: EditText
    lateinit var edtTotalPage: EditText //총 페이지 수

    lateinit var rg_Color: RadioGroup
    lateinit var rb_red: RadioButton
    lateinit var rb_orange: RadioButton
    lateinit var rb_yellow: RadioButton
    lateinit var rb_green: RadioButton
    lateinit var rb_blue: RadioButton
    lateinit var rb_navy: RadioButton
    lateinit var rb_purple: RadioButton
    lateinit var rb_pink: RadioButton




    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_book_reg, container, false)

        edtTitle = view.findViewById(R.id.edtTitle)
        edtAuthor = view.findViewById(R.id.edtAuthor)
        edtTotalPage = view.findViewById(R.id.edtTotalPage)

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

        btnRegister = view.findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            var str_title: String = edtTitle.text.toString()
            var str_author: String = edtAuthor.text.toString()
            var int_totalPage = Integer.parseInt(edtTotalPage.text.toString())
            var str_color = ""

            var int_nowPage = arguments?.getInt("nowPage")
            var int_accumPage = 0
            if (int_nowPage != null) {
                int_accumPage += int_nowPage
            }


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


            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO bookDB VALUES ('"+str_color+"', 'aa', '"+str_title+"', '"+str_author+"', "+int_totalPage+", "+int_nowPage+", "+int_accumPage+");")
            sqlitedb.close()

            val listFragment = ListFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, listFragment)
            transaction.commit()


        }
        return view
    }
}