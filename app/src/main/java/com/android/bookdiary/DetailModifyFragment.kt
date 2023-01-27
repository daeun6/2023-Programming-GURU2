package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_daily_memo.*


class DetailModifyFragment : Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var dDate = arguments?.getString("dDate")
        var dTitle = arguments?.getString("dTitle")

        var dTotalPage = arguments?.getString("dTotalPage")
        var accumPage = arguments?.getInt("dAccumPage")



        val view = inflater.inflate(R.layout.fragment_detail_modify, container, false)

        val pageEdit = view.findViewById<EditText>(R.id.editTextNumber)
        val sentenceEdit = view.findViewById<EditText>(R.id.likeSentence)
        val thinkEdit = view.findViewById<EditText>(R.id.myThink)

        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        val pageFind = "SELECT writeDB FROM dPage WHERE (( dDate = '" + dDate + "') and ( dTitle = '" + dTitle + "')"
        val mPage = sqlitedb.rawQuery(pageFind, null)

        val sentenceFind = "SELECT writeDB FROM dSentence WHERE (( dDate = '" + dDate + "') and ( dTitle = '" + dTitle + "')"
        val mSentence = sqlitedb.rawQuery(sentenceFind, null)

        val thinkFind = "SELECT writeDB FROM dThink WHERE (( dDate = '" + dDate + "') and ( dTitle = '" + dTitle + "')"
        val mThink = sqlitedb.rawQuery(thinkFind, null)

        sqlitedb.close()
        dbManager.close()

        pageEdit.setText(mPage.getInt(0))
        sentenceEdit.setText(mSentence.getString(0))
        thinkEdit.setText(mThink.getString(0))



        // DetailFragment로 돌아가기
        val dailyBackBtn = view.findViewById<Button>(R.id.doneBtn)
        dailyBackBtn.setOnClickListener {

            val detailFragments = DetailFragment()
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

                sqlitedb.execSQL("UPDATE wirteDB SET dNowPage = '" + dPage + "', dSentence = '" + dSentence + "', dThink = '" + dThink + "', accumPage = '" + accumPage + "' WHERE ( title = '" + dTitle + "') and ( dTitle = '" + dTitle + "');")

                sqlitedb.close()
                dbManager.close()

                transaction.replace(R.id.container, detailFragments)
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




            val detailFragment = DetailFragment()
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            ft.replace(R.id.container, detailFragment).commit()
        }

        return view
    }

}