package com.fingerth.basislib.common.adapter

import android.content.Context
import com.fingerth.ktadapter.recycleradapter.CommonRecyclerAdapter
import com.fingerth.ktadapter.recycleradapter.Holder

class Xadapter<T>(private val con: Context) {
    fun data(mData: List<T>): WithData<T> = WithData(con, mData)

    class WithData<T>(private val con: Context, private val mData: List<T>) {
        fun layoutId(layoutId: Int): WithLayout<T> = ViewTypeBuider().typeBy { 1 }.typeItem(1 to layoutId).build()

        fun ViewTypeBuider(): ItemStyleBuilder<T> = ItemStyleBuilder(con, mData)

        class ItemStyleBuilder<T>(private val con: Context, private val mData: List<T>) {
            private var type: (T) -> Any = {}
            private val list by lazy { ArrayList<Pair<Any, Int>>() }

            fun typeBy(type: (T) -> Any): ItemStyleBuilder<T> {
                this.type = type
                return this
            }

            fun typeItem(p: Pair<Any, Int>): ItemStyleBuilder<T> {
                list.add(p)
                return this
            }


            fun build(): WithLayout<T> = WithLayout(con, mData, type, list)
        }

    }

    class WithLayout<T>(private val con: Context, private val mData: List<T>,
                        private val type: (T) -> Any = {},
                        private val list: ArrayList<Pair<Any, Int>>) {
        private val mapBind = HashMap<Any, (Context, Holder, List<T>, T, Int) -> Unit>()

        fun bind(any: Any?, black: (Context, Holder, List<T>, T, Int) -> Unit): WithLayout<T> {
            mapBind[any ?: 1] = black
            return this
        }

        fun create(): CommonRecyclerAdapter<T> = object : CommonRecyclerAdapter<T>(con, mData) {
            override fun onBind(holder: Holder, p: Int, rb: T) {
                mapBind[type(rb)]?.let { it(context, holder, mDataList, rb, p) }
            }

            override fun setLayoutId(p0: Int): Int = list[p0].second
            override fun itemViewType(p: Int): Int {
                for ((type, t) in list.withIndex()) {
                    if (t.first == type(mDataList[p]))
                        return type
                }
                return super.itemViewType(p)
            }
        }
    }


}