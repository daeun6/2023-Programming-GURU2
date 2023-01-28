package com.android.bookdiary

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

data class BookListData(
    val title: String, //textView
    val accumPage: Int,
    val totalPage: Int,
    var colorView: String,
    val progressBar: Float
)