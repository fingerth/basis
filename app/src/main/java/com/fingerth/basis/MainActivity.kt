package com.fingerth.basis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fingerth.basislib.Shared
import com.fingerth.basislib.click
import com.fingerth.basislib.ktStartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1 click { ktStartActivity<MainActivity2>(this) }

        Shared.put(this, "123", "123")
        Shared.put(this, "456", 456)
        Shared.put(this, "789", 789f)
        Shared.put(this, "000", true)
    }
}