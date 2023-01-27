package com.android.bookdiary

import java.time.LocalDate

interface OnitemListener {
    fun onItemClick(dayText: LocalDate)
}