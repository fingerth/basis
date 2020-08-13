package com.fingerth.basis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fingerth.basis.utils.pic
import com.fingerth.basislib.Shared
import com.fingerth.basislib.click
import com.fingerth.basislib.ktStartActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private val list1: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this).load(pic).into(banner)
        card1 click {

        }
    }

}