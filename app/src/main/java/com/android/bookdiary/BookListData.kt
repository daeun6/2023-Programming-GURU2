package com.android.bookdiary

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

// 책 리스트를 보여주는 프래그먼트의 리사이클러뷰의 하나의 아이템에 들어갈 데이터
data class BookListData(
    val title: String, //textView
    val accumPage: Int,
    val totalPage: Int,
    var colorView: String,
    val progressBar: Float
)