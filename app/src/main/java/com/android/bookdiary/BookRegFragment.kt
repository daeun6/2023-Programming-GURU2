package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
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
    lateinit var edtPage: EditText //총 페이지 수

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



//        btnColor = view.findViewById(R.id.btnColor)
//        btnColor.setOnClickListener {
//            val builder = AlertDialog.Builder(activity)
//            builder.setTitle("색상 지정")
//            val inflater: LayoutInflater = layoutInflater
//            builder.setView(inflater.inflate(R.layout.color_dialog, null))
//
//            val alertDialog: AlertDialog = builder.create()
//            alertDialog.show()
//
//            btnRed.setOnClickListener {
//                val color = ResourcesCompat.getColor(getResources(), R.color.bookRed, null)
//                Toast.makeText(context, "빨간색 선택", Toast.LENGTH_SHORT).show()
//                //btnColor.setBackgroundColor(color)
//            }
//
//        }

//        val color = ResourcesCompat.getColor(getResources(), R.color.bookRed, null)
//
//        btnRed.setOnClickListener {
//            btnColor.setBackgroundColor(color)
//        }

        edtTitle = view.findViewById(R.id.edtTitle)
        edtAuthor = view.findViewById(R.id.edtAuthor)
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

        btnRegister = view.findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            var str_title: String = edtTitle.text.toString()
            var str_author: String = edtAuthor.text.toString()
            var str_page: String = edtPage.text.toString()
            var str_color = ""

            if(rg_Color.checkedRadioButtonId == R.id.rbRed) {
                str_color = "red"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbOrange) {
                str_color = "orange"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbYellow) {
                str_color = "yellow"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbGreen) {
                str_color = "green"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbBlue) {
                str_color = "blue"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbNavy) {
                str_color = "navy"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbPurple) {
                str_color = "purple"
            }

            if(rg_Color.checkedRadioButtonId == R.id.rbPink) {
                str_color = "pink"
            }

//            radioGroup.setOnCheckedChangeListener { group, checkedId ->
//                when (checkedId) {
//                    R.id.rbRed -> str_color = "red"
//                    R.id.rbOrange -> str_color = "orange"
//                    R.id.rbYellow -> str_color = "yellow"
//                    R.id.rbGreen -> str_color = "green"
//                    R.id.rbBlue -> str_color = "blue"
//                    R.id.rbNavy -> str_color = "navy"
//                    R.id.rbPurple -> str_color = "purple"
//                    R.id.rbPink -> str_color = "pink"
//                }
//            }

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO bookDB VALUES ('"+str_title+"', '"+str_author+"', "+str_page+", NULL, NULL, '"+str_color+"', NULL);") //모든 필드에 해당하는 값 다 받아와야 오류 안 뜰 듯..
            sqlitedb.close()

            val listFragment = ListFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, listFragment)
            transaction.commit()


        }
        return view
    }
}