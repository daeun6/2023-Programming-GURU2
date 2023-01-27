package com.android.bookdiary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int

): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE bookDB (color CHAR(20) NOT NULL, id CHAR(20), title CHAR(30) PRIMARY KEY, author CHAR(30), totalPage INTEGER, nowPage INTEGER, accumPage INTEGER)")
        db!!.execSQL("CREATE TABLE readingDB (category CHAR(20), cases INTEGER, totalAvg INTEGER, readAvg INTEGER)")
        db!!.execSQL("CREATE TABLE userDB (user CHAR(20) PRIMARY KEY, id CHAR(20), password CHAR(20))")
        db!!.execSQL("CREATE TABLE writeDB (dUser CHAR(30), dNowPage INTEGER, dSentence CHAR(100), dThink CHAR(100), dDate CHAR(20), dTitle CHAR(30), dColor CHAR(20), dTotalPage INTEGER)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS bookDB")
        db!!.execSQL("DROP TABLE IF EXISTS readingDB")
        db!!.execSQL("DROP TABLE IF EXISTS userDB")
        db!!.execSQL("DROP TABLE IF EXISTS writeDB")
        onCreate(db)

    }

}