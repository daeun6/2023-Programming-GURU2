package com.android.bookdiary

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

data class NoteData( //3
    val title: String, //textView
    //val progressBar: ProgressBar,
    val nowPage: Int,
    val totalPage: Int,
    var colorView: String
)