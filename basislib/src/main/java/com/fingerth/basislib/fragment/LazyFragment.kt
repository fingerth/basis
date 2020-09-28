package com.fingerth.basislib.fragment

import androidx.fragment.app.Fragment
import com.fingerth.basislib.ThreadRipper

/**
 * 适用v4
 * ViewPager+Fragment 懒加载页面
 * tag：     一般表示 -> 位置
 * target：  表示 -> 目标位置
 */
abstract class LazyFragment(private val tag: Int = 0, private var target: Int = 0) : Fragment() {

    /**
     * 第一次显示界面
     */
    abstract fun onFirstVisible()


    private var noVisibleOne: Boolean = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (noVisibleOne && isVisibleToUser) {
            when (tag) {
                0 -> if (target == 0) show() else target = 0
                else -> show()
            }
        }
    }

    private fun show() {
        noVisibleOne = false
        if (view == null || activity == null) {
            ThreadRipper.getThreadPool {
                while (view == null || activity == null) {
                    Thread.sleep(100)
                }
                activity!!.runOnUiThread { onFirstVisible() }
            }
        } else onFirstVisible()
    }
}