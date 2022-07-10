package com.example.shoppingandroidapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        * activity가 생성되면 생성된 시점에 layout을 inflate해야 한다.
        * 그런데 스플래시 액티비티는 layout file을 통해서 layout을 만드는 것이 아니라 drawable을 통해서 만들것
        * */
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}