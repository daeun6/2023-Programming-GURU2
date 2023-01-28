package com.android.bookdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({//   일정 시간이 경과되면 화면이 메인 화면으로 전환되도록 함
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()//  화면 전환 후 finish()
        },DURATION)

    }
    companion object {
        private const val DURATION : Long = 2000 // 2초 동안
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}