package com.fingerth.basislib.common.designmode.facade

//外观
//经脉
class JingMai {
    fun jingmai() {
        println("开启经脉")
    }
}

//内功
class NeiGong {
    fun jiuYang() {
        println("使用九阳神功")
    }

    fun qianKun() {
        println("使用乾坤大挪移")
    }
}

//招式
class ZhaoShi {
    fun taiJi() {
        println("使用招式太极拳")
    }

    fun qiShangQuan() {
        println("使用招式七伤拳")
    }

    fun shengHuoLing() {
        println("使用招式圣火令")
    }
}

//张无忌
class ZhangWuJi {
    private val jm = JingMai()
    private val ng = NeiGong()
    private val zs = ZhaoShi()

    //使用乾坤大挪移
    fun qianKun() {
        jm.jingmai()
        ng.qianKun()
    }

    //使用七伤拳
    fun qiShang() {
        jm.jingmai()
        ng.jiuYang()
        zs.qiShangQuan()
    }
}

fun main() {
    val zwj = ZhangWuJi()
    zwj.qianKun()//张无忌使用乾坤大挪移
    zwj.qiShang()//张无忌使用七伤拳
}


