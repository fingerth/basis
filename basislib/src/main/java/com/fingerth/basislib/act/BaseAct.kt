package com.fingerth.basislib.act

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseAct(private val layoutId: Int, private val e: Boolean = false) :
    AppCompatActivity() {
    private lateinit var loadUtils: StepByStepLoadUtils
    private lateinit var loadingHelper: LoadingHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        initToolbar()
        initView()
        if (e) {
            eventBusRegister()
        }
        loadUtils = StepByStepLoadUtils()
        loadUtils stepLoad { load { lazyLoad() } }
    }

    override fun setContentView(layoutResID: Int) {
        setContentView(layoutResID, android.R.id.content)
    }

    open fun setContentView(
        layoutResID: Int,
        @IdRes contentViewId: Int,
        contentAdapter: LoadingHelper.ContentAdapter<*>? = null
    ) {
        super.setContentView(layoutResID)
        loadingHelper =
            LoadingHelper((findViewById<ViewGroup>(contentViewId)).getChildAt(0), contentAdapter)
        loadingHelper.setOnReloadListener { this.onReload() }
    }

    open fun onReload() {}

    override fun onDestroy() {
        super.onDestroy()
        if (e) {
            eventBusUnregister()
        }
        loadUtils.clear()
    }

    abstract fun initToolbar()
    abstract fun initView()
    abstract fun eventBusRegister()
    abstract fun eventBusUnregister()
    open fun lazyLoad() {}

    //注：right1优先于right2，即：右边只有一个按钮，给right1赋值。不考虑right1为空，right2赋值的情况。
//    fun bar(
//        title: String,
//        right1: String = "",
//        right2: String = "",
//        block2: () -> Unit = {},
//        block1: () -> Unit = {}
//    ) = bar(TitleBarAdapter(title, right1, right2, block2, block1))
//
//    private fun bar(helper: LoadingHelper.Adapter<*>) {
//        loadingHelper.register(ViewType.TITLE, helper)
//        loadingHelper.setDecorHeader(ViewType.TITLE)
//    }

}