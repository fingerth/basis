package com.fingerth.basislib.common.designmode.factory
//工厂
abstract class BaseFactory {
    abstract fun <T> create(clz: Class<T>)
}

class MyFactory : BaseFactory() {
    override fun <T> create(clz: Class<T>) {
        var t: T? = null
        try {
            t = Class.forName(clz.name).newInstance() as T
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}