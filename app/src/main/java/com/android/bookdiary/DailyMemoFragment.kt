package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        // DailyFragment에서 전달한 값 받아오기
        var dUser = arguments?.getString("dUser")
        var dDate = arguments?.getString("dDate")
        var dTitle = arguments?.getString("dTitle")
        var dColor = arguments?.getString("dColor")
        var dTotalPage = arguments?.getString("dTotalPage")
        var accumPage = arguments?.getInt("dAccumPage")

        // 작성 완료 버튼
        val dailyDoneBtn = view.findViewById<Button>(R.id.dailyDoneBtn)
        dailyDoneBtn.setOnClickListener {

            val mainFragment = MainFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

            var page = editTextNumber.text.toString()

            // 페이지 수 입력시에만 DB로 입력 값 전달하기
            if (page != "") {
                var dPage = page.toInt()
                accumPage = accumPage?.plus(dPage)
                var dSentence: String = likeSentence.text.toString()
                var dThink: String = myThink.text.toString()

                dbManager = DBManager(activity, "bookDB", null, 1)
                sqlitedb = dbManager.writableDatabase

                sqlitedb.execSQL("INSERT INTO writeDB VALUES ('" + dUser + "', '" + dPage + "', '" + dSentence + "', '" + dThink + "', '" + dDate + "', '" + dTitle + "', '" + dColor + "', '" + dTotalPage + "');")
                sqlitedb.execSQL("UPDATE bookDB SET nowPage = '" + dPage + "', accumPage = '" + accumPage + "' WHERE ( title = '" + dTitle + "');")

                sqlitedb.close()
                dbManager.close()

                transaction.replace(R.id.container, mainFragment)
                transaction.commit()
            }

            // 페이지 수 미입력시 팝업 띄우기
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
        val dailyBackBtn = view.findViewById<Button>(R.id.doneBtn)
        dailyBackBtn.setOnClickListener {
            val dailyFragment = DailyFragment()

            // 이전으로 돌아가도 날짜 정보가 유지되도록 bundle로 값 던지기
            var bundle = Bundle()
            bundle.putString("dDate", dDate)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            dailyFragment.arguments = bundle
            ft.replace(R.id.container, dailyFragment).commit()
            Toast.makeText(activity, dDate, Toast.LENGTH_SHORT).show()
        }

        return view

    }

}
