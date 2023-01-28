package com.android.bookdiary

import android.os.Build
import java.time.LocalDate

class CalendarUtil {

    companion object{
        var selectedDate: LocalDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()//   로컬 컴퓨터의 현재 날짜 가져오기
        } else {
            TODO("VERSION.SDK_INT < O")
        }

    }
}