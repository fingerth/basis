package com.fingerth.basislib.common.designmode.decoration
//装饰
//武侠人物
abstract class Swordsman {
    abstract fun attackMagic() //使用武功
}

//杨过
class YangGuo : Swordsman() {
    override fun attackMagic() {
        println("杨过使用全真剑法")
    }
}

//装饰者
abstract class Master(private val man: Swordsman) : Swordsman() {
    override fun attackMagic() {
        man.attackMagic()
    }
}

//洪七公
class HongQiGong(man: Swordsman) : Master(man) {

    override fun attackMagic() {
        super.attackMagic()
        teachAttackMagic()
    }

    private fun teachAttackMagic() {
        println("洪七公教授打狗棒法")
        println("杨过使用打狗棒法")
    }
}


//欧阳锋
class OuYangFeng(man: Swordsman) : Master(man) {

    override fun attackMagic() {
        super.attackMagic()
        teachAttackMagic()
    }

    private fun teachAttackMagic() {
        println("欧阳锋教授蛤蟆功")
        println("杨过使用蛤蟆功")
    }
}











