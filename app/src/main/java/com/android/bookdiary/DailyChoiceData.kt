package com.android.bookdiary


// DailyChoice 프래그먼트에서 사용할 데이터
data class DailyChoiceData (

    val bookColor : String,
    val bookTitle : String,
    val date : String,
    val totalPage : Int,
    val accumPage : Int
)