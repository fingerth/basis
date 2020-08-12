package com.fingerth.basislib.act

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference
import java.util.*

/**
 * ======================================================
 * Created by admin fingerth on 2020/07/21.
 * <p/>
 * <p>
 */
class StepByStepLoadUtils {
    private val DEF_DELAYED: Long = 100

    //队列保存任务
    private var queue: Queue<RunnableBean>? = LinkedList()
    private var handler: StepHandler? = null


    private class StepHandler(queue: Queue<RunnableBean>?) : Handler() {
        private var mTargetRef: WeakReference<Queue<RunnableBean>>?

        init {
            mTargetRef = WeakReference<Queue<RunnableBean>>(queue)
        }

        override fun handleMessage(msg: Message) {
            mTargetRef?.let {
                val queue = it.get() ?: return
                //执行当前任务
                queue.poll().runnable.invoke()
                //延时执行下一个任务
                val runNext = queue.peek() ?: return
                sendEmptyMessageDelayed(1, runNext.delayed)
            }
        }

        // 清除所有任务
        fun clear() {
            mTargetRef?.clear()
            mTargetRef = null
        }
    }

    /**
     * 设置一个任务
     */
    fun load(millisecond: Long = DEF_DELAYED, runnable: () -> Unit) = queue!!.offer(RunnableBean(millisecond, runnable))

    fun start() {
        check(handler == null) { "Can only be started once !" }
        handler = StepHandler(queue)
        handler!!.sendEmptyMessageDelayed(1, queue!!.peek().delayed)
    }

    infix fun stepLoad(block: StepByStepLoadUtils.() -> Unit) {
        this.block()
        start()
    }

    /**
     * 清掉所有任务
     */
    fun clear() {
        handler!!.clear()
        handler = null
        queue!!.clear()
        queue = null
    }


    inner class RunnableBean(val delayed: Long, val runnable: () -> Unit = {})
}