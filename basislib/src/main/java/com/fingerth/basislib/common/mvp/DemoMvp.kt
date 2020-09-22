package com.fingerth.basislib.common.mvp

interface DemoImModel : ImModel {
    fun reply()
}

interface DemoView : ImView<DemoImPresenter> {
    fun onRvAdapter()
}


interface DemoImPresenter : ImPresenter {
    fun getData()
    fun reply()
}
