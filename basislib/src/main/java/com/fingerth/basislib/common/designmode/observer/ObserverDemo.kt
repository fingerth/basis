package com.fingerth.basislib.common.designmode.observer

//观察者
//抽象观察者，定义一个更新方法
interface Observer {
    fun update(message: String)
}

//具体观察者
class User(private val name: String) : Observer {
    override fun update(message: String) {
        println("$name - $message")
    }
}

//抽象被观察者
interface Subject {
    fun attach(obs: Observer)//添加订阅者
    fun detach(obs: Observer)//删除订阅者
    fun notify(message: String)//更新消息
}

//具体观察者
class SubSubject(private val userList: ArrayList<Observer> = ArrayList()) : Subject {
    override fun attach(obs: Observer) {
        userList.add(obs)
    }

    override fun detach(obs: Observer) {
        userList.remove(obs)
    }

    override fun notify(message: String) {
        for (user in userList) {
            user.update(message)
        }
    }
}

fun main() {
    val subject = SubSubject()
    val user1 = User("YangGuo")
    val user2 = User("HongQiGong")
    val user3 = User("ZhangSanFong")
    subject.attach(user1)
    subject.attach(user2)
    subject.attach(user3)
    subject.notify("看招")
}

