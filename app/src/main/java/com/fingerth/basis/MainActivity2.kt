package com.fingerth.basis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fingerth.basislib.Shared
import com.fingerth.basislib.click
import com.fingerth.basislib.ktStartActivity
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button1 click {
            println("String : " + Shared.getString(this, "123"))
            println("Int : " + Shared.getInt(this, "456"))
            println("Float : " + Shared.getFloat(this, "789"))
            println("Boolean : " + Shared.getBoolean(this, "000"))

            println("Stringxx : " + Shared.getString(this, "123xx","123xx"))
            println("Intxx : " + Shared.getInt(this, "456xx",777))
            println("Floatxx : " + Shared.getFloat(this, "789xx"))
            println("Booleanxx : " + Shared.getBoolean(this, "xx123",true))
        }
    }
}