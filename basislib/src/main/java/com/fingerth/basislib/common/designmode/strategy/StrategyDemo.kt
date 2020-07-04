package com.fingerth.basislib.common.designmode.strategy

//策略
//与强者战斗
interface FightingStrategy {
    fun fighting()
}

//3个策略 ，弱，普通，强
class WeakRivalStrategy : FightingStrategy {
    override fun fighting() {
        println("弱对手，张无忌使用太极剑")
    }
}

class NormalRivalStrategy : FightingStrategy {
    override fun fighting() {
        println("普通对手，张无忌使用圣火令神功")
    }
}

class StrongRivalStrategy : FightingStrategy {
    override fun fighting() {
        println("强大对手，张无忌使用乾坤大挪移")
    }
}

//上下文角色
class MyContext(private val s: FightingStrategy) {
    fun fighting() {
        s.fighting()
    }
}

fun main() {
    var con: MyContext? = null
    con = MyContext(WeakRivalStrategy())
    con.fighting()
    con = MyContext(NormalRivalStrategy())
    con.fighting()
    con = MyContext(StrongRivalStrategy())
    con.fighting()
}



