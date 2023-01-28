package com.android.bookdiary

import java.time.LocalDate

interface OnitemListener { //   날짜 넘기는 인터페이스
    fun onItemClick(dayText: LocalDate)
}