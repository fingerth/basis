package com.fingerth.basislib

import java.util.concurrent.*

object ThreadRipper {

    /**
     * 新建线程时，必须通过线程池提供（AsyncTask 或者 ThreadPoolExecutor 或者其他形式自定义的线程池），不允许在应用中自行显式创建线程。
     * 说明：
     * 使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致
     * 消耗完内存或者“过度切换”的问题。另外创建匿名线程不便于后续的资源使用分析，对性能分析等会造成困扰。
     *
     */
    fun getThreadPool(run: () -> Unit) {
        val executorService: ExecutorService = ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            1, TimeUnit.SECONDS, LinkedBlockingQueue(),
            RejectedExecutionHandler { _, _ -> Thread { run() }.start() })
        //执行任务
        executorService.execute { run() }
    }


}