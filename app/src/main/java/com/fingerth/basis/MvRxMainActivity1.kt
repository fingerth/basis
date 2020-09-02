package com.fingerth.basis

import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.fingerth.basis.fragment.MvRxFragment1
import com.fingerth.basislib.fragment.adpter.FragmentAdapterDemo2
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


class MvRxMainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mv_rx_main1)
//        Glide.with(this).load("").into(null)
//        OkHttpUrlLoader
        val adapter = FragmentAdapterDemo2(supportFragmentManager, R.id.fragmentContentId) {
            MvRxFragment1()
        }
    }
}