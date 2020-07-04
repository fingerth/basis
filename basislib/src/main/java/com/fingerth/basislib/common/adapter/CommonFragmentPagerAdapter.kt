package com.fingerth.basislib.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CommonFragmentPagerAdapter(
    manager: FragmentManager,
    private val listFrg: ArrayList<Fragment>,
    private val titles: Array<String>? = null
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = listFrg[position]

    override fun getCount(): Int = listFrg.size

    override fun getPageTitle(position: Int): CharSequence? = titles?.run { titles[position] }

}