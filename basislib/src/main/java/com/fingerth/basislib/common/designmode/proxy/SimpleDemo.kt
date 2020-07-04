package com.fingerth.basislib.common.designmode.proxy
//代理
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

interface ProxyDemo {
    fun proxy()
}

class MyProxy : ProxyDemo {
    override fun proxy() = println("xxix,MyProxy")
}

class YourProxy(private val p: ProxyDemo) : ProxyDemo {
    override fun proxy() = p.proxy()
}

class DynamicPurchasing(private val any: Any) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? = method?.run { invoke(any, *args.orEmpty()) }
}

fun main() {
    val myProxy: MyProxy = MyProxy()
    val purchasing: DynamicPurchasing = DynamicPurchasing(myProxy)
    val newProxyInstance = Proxy.newProxyInstance(myProxy.javaClass.classLoader, myProxy.javaClass.interfaces, purchasing) as ProxyDemo
    newProxyInstance.proxy()
}


