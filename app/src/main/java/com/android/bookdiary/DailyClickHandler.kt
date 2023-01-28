package com.android.bookdiary

// DailyChoiceFragment 내의 아이템을 클릭할 수 있도록 하는 Handler
interface DailyClickHandler {
    fun clickedBookItem(book: DailyChoiceData)
}