package com.fingerth.basislib.common.designmode.flyweight

//享元
//商品
interface IGoods {
    fun showPrice(version: String)
}

//商品
class Goods(private val name: String) : IGoods {
    override fun showPrice(version: String) {
        when (version) {
            "32G" -> println("价格为5199")
            "128G" -> println("价格为5999")
        }
    }
}

//享元工厂
object GoodsFactory {
    private val pool = HashMap<String, Goods>()
    fun getGoods(name: String): Goods? {
        return if (pool.containsKey(name)) pool[name] else {
            val goods = Goods(name)
            pool[name] = goods
            goods
        }
    }
}


fun main() {
   val g1 =  GoodsFactory.getGoods("iphone7")
    g1?.showPrice("32G")
    val g2 =  GoodsFactory.getGoods("iphone7")
    g2?.showPrice("128G")
}
