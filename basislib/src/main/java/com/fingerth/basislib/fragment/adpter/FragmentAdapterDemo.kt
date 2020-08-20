package com.fingerth.basislib.fragment.adpter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FragmentAdapterDemo(private val manager: FragmentManager, private val fragmentContentId: Int, private val data: List<XBean>) {
    private val sArray: SparseArray<Fragment> = SparseArray()
    private var currentTab = 0 // 当前选中的Tab页面索引
    private val trans: FragmentTransaction = manager.beginTransaction()

    init {
        changeFragment(0)
    }


    fun changeFragment(p: Int) {
        if (sArray.get(p) == null) {
            val frag = Xx(data[p].id)
            sArray.put(p, frag)
            trans.add(fragmentContentId, frag)
        } else {
            sArray.get(currentTab).onPause()
            if (sArray.get(p).isAdded) sArray.get(p).onResume() else trans.add(fragmentContentId, sArray.get(p))
        }
        for (i in 0 until sArray.size()) {
            if (sArray.keyAt(i) == p) trans.show(sArray.get(p)) else trans.hide(sArray.get(sArray.keyAt(i)))
        }
        currentTab = p
        trans.commitAllowingStateLoss()
    }

    class Xx(id: String) : Fragment()
    class XBean(val id: String)

}