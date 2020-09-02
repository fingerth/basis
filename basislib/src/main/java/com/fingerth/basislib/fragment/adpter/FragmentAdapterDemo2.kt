package com.fingerth.basislib.fragment.adpter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentAdapterDemo2(private val manager: FragmentManager, private val fragmentContentId: Int, private val getFragment: (Int) -> Fragment) {
    private val sArray: SparseArray<Fragment> = SparseArray()
    private var currentTab = -1 // 当前选中的Tab页面索引

    init {
        changeFragment(0)
    }

    fun changeFragment(p: Int) {
        if (currentTab == p) return
        manager.beginTransaction().apply {
            if (sArray.get(p) == null) {
                val frag = getFragment(p)
                sArray.put(p, frag)
                add(fragmentContentId, frag)
            } else {
                sArray.get(currentTab).onPause()
                if (sArray.get(p).isAdded) sArray.get(p).onResume() else add(fragmentContentId, sArray.get(p))
            }
            for (i in 0 until sArray.size()) {
                if (sArray.keyAt(i) == p) show(sArray.get(p)) else hide(sArray.get(sArray.keyAt(i)))
            }
            currentTab = p
            commitAllowingStateLoss()
        }
    }

}