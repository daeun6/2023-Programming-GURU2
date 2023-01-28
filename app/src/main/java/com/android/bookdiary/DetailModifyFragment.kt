package com.android.bookdiary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_daily_memo.*


class DetailModifyFragment : Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    var dPage: Int = 0
    lateinit var dSentence: String
    lateinit var dThink: String
    lateinit var dTitle: String
    lateinit var dDate: String
    lateinit var dAccumPageString: String
    lateinit var dNowPageString : String

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detail_modify, container, false)
        val pageEdit = view.findViewById<EditText>(R.id.editTextNumber)
        val sentenceEdit = view.findViewById<EditText>(R.id.likeSentence)
        val thinkEdit = view.findViewById<EditText>(R.id.myThink)

        dTitle = arguments?.getString("dTitle").toString()
        dDate = arguments?.getString("dDate").toString()
        dbManager = DBManager(activity, "bookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM writeDB WHERE dTitle = '" + dTitle +"';", null)

        if (cursor.moveToNext()){
            dSentence = cursor.getString(cursor.getColumnIndex("dSentence")).toString()
            dPage = cursor.getInt(cursor.getColumnIndex("dNowPage"))
            dThink = cursor.getString(cursor.getColumnIndex("dThink")).toString()
            dNowPageString = cursor.getString(cursor.getColumnIndex("dNowPage"))
        }

        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB WHERE title = '" + dTitle +"';", null)

        if (cursor.moveToNext()){
            dAccumPageString = cursor.getString(cursor.getColumnIndex("accumPage"))
        }

        sqlitedb.close()
        dbManager.close()

        var dAccumPage = dAccumPageString.toInt()
        var dNowPage = dNowPageString.toInt()

        dAccumPage -= dNowPage

        pageEdit.text = Editable.Factory.getInstance().newEditable(dPage.toString())
        sentenceEdit.text = Editable.Factory.getInstance().newEditable(dSentence.toString())
        thinkEdit.text = Editable.Factory.getInstance().newEditable(dThink.toString())


        // DetailFragment로 돌아가기
        val dailyBackBtn = view.findViewById<Button>(R.id.doneBtn)
        dailyBackBtn.setOnClickListener {

            var mPage = editTextNumber.text.toString()

            // 페이지 수 입력시에만 DB로 입력 값 전달하기
            if (mPage != "") {
                var mPage = mPage.toInt()
                var mAccumPage : Int = dAccumPage
                mAccumPage += mPage
                var mSentence: String = likeSentence.text.toString()
                var mThink: String = myThink.text.toString()

                dbManager = DBManager(activity, "bookDB", null, 1)
                sqlitedb = dbManager.writableDatabase

                sqlitedb.execSQL("UPDATE writeDB SET dNowPage = '" + mPage + "', dSentence = '" + mSentence + "', dThink = '" + mThink + "' WHERE dTitle = '" + dTitle +"';")
                sqlitedb.execSQL("UPDATE bookDB SET accumPage = '" + mAccumPage + "' WHERE title = '" + dTitle +"';")

                sqlitedb.close()
                dbManager.close()

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

            var title = dTitle
            var dDate = dDate
            var bundle = Bundle()
            bundle.putString("dDate", dDate)
            bundle.putString("title", title)
            val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

            var detailFragment = DetailFragment()
            detailFragment.arguments = bundle
            ft.replace(R.id.container, detailFragment).commit()
            Toast.makeText(activity, dDate, Toast.LENGTH_SHORT).show()
        }

        return view
    }

}