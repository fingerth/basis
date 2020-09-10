package com.fingerth.basislib.common.designmode.concrete

//模板
//AbstractClass：抽象类，定义了一套算法框架 ；  ConcreteClass:具体实现类
abstract class AbstractSwordsman {
     fun fighting() {
        //运行内功
        neigong()
        //调整经脉
        meridian()
        //如果有武器，准备武器
        if (hasWeapons()) {
            weapons()
        }
        //使用招式
        moves()
        //钩子方法
        hook()
    }

    abstract fun neigong()
    fun meridian() {
        println("开启经脉")
    }

    abstract fun weapons()
    abstract fun moves()
    fun hook() {}

    open fun hasWeapons() = true
}

//张无忌
class ZhangWuJi : AbstractSwordsman() {
    override fun neigong() {

    }

    override fun weapons() {
    }

    override fun moves() {
    }

    override fun hasWeapons(): Boolean = false//没有武器

}
