
package com.android.bookdiary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBManager( //4
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    // DB 생성
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE bookDB (color CHAR(20) NOT NULL, id CHAR(20), title CHAR(30) PRIMARY KEY, author CHAR(30), totalPage INTEGER, nowPage INTEGER, accumPage INTEGER)")  // 책 목록을 관리하는 테이블
        db!!.execSQL("CREATE TABLE readingDB (category CHAR(20), cases INTEGER, totalAvg INTEGER, readAvg INTEGER)")    // 성인 평균 연간 독서량 공공데이터 테이블
        db!!.execSQL("CREATE TABLE writeDB (dUser CHAR(30), dNowPage INTEGER, dSentence CHAR(100), dThink CHAR(100), dDate CHAR(20), dTitle CHAR(30), dColor CHAR(20), dTotalPage INTEGER)")    // 일일 독후감 데이터 테이블
    }

    // DB 수정
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS bookDB")
        db!!.execSQL("DROP TABLE IF EXISTS readingDB")
        db!!.execSQL("DROP TABLE IF EXISTS writeDB")
        onCreate(db)


    }


    }

