package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_daily_memo.*


private const val TAG = "DailyMemoFragment"

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
        Log.d(TAG, "제발 널 아니길: ${dTotalPage}")

        // 작성 완료 버튼
        val dailyDoneBtn = view.findViewById<Button>(R.id.dailyDoneBtn)
        dailyDoneBtn.setOnClickListener {

            val mainFragment = MainFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

            var page = editTextNumber.text.toString()
            Log.d(TAG, ": ${page}")

            if (page != "") {

                var dPage = page.toInt()
                var dSentence: String = likeSentence.text.toString()
                var dThink: String = myThink.text.toString()

                dbManager = DBManager(activity, "bookDB", null, 1)
                sqlitedb = dbManager.writableDatabase



                sqlitedb.execSQL("INSERT INTO writeDB VALUES ('" + dUser + "', '" + dPage + "', '" + dSentence + "', '" + dThink + "', '" + dDate + "', '" + dTitle + "', '" + dColor + "', '" + dTotalPage + "');")
                sqlitedb.close()
                dbManager.close()

                transaction.replace(R.id.container, mainFragment)
                transaction.commit()

            }
            else {
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
