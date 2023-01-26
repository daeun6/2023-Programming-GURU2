package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_daily_memo.*


class DailyMemoFragment : Fragment() {


    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_daily_memo, container, false)

        var dUser = arguments?.getString("dUser")
        var dDate = arguments?.getString("dDate")
        var dTitle = arguments?.getString("dTitle")
        var dColor = arguments?.getString("dColor")
        var dTotalPage = arguments?.getString("dTotalPage")

        // 작성 완료 버튼
        val dailyDoneBtn = view.findViewById<Button>(R.id.dailyDoneBtn)
        dailyDoneBtn.setOnClickListener {
            val mainFragment = MainFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

            dbManager = DBManager(activity, "bookDB", null, 1)
            sqlitedb = dbManager.writableDatabase

            var dPage = Integer.parseInt(editTextNumber.text.toString())
            var dSentence: String = likeSentence.text.toString()
            var dThink : String = likeSentence.text.toString()

            sqlitedb.execSQL("INSERT INTO writeDB VALUES ('" + dUser + "', '" + dPage + "', '" + dSentence + "', '" + dThink + "', '" + dDate + "', '" + dTitle + "', '" + dColor + "', '" + dTotalPage + "');")
            sqlitedb.close()
            dbManager.close()

            transaction.replace(R.id.container, mainFragment)
            transaction.commit()

        }

        // 책 다시 선택하도록 돌아가는 버튼
        val dailyBackBtn = view.findViewById<Button>(R.id.dailyBackBtn)
        dailyBackBtn.setOnClickListener {
            val dailyFragment = DailyFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, dailyFragment)
            transaction.commit()
        }

        return view

    }

}
