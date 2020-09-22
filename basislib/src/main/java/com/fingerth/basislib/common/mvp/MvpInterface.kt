package com.fingerth.basislib.common.mvp


interface ImModel
interface ImPresenter
interface ImView<out P : ImPresenter> {
    fun getP(): P
}

abstract class AbsPresenter<V : ImView<ImPresenter>, M : ImModel>(val view: V) {
    val model: M by lazy { getM() }
    abstract fun getM(): M

}