package com.chelun.soa.thread;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

public class Threadpool {
	private static final long DEFAULT_KEEPALIVE_MINUTES = 30L;

    private ExecutorService   executorService;

    private ThreadFactory     threadFactory;

    public Threadpool(String poolName, int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue) {
        this(poolName, corePoolSize, maximumPoolSize, DEFAULT_KEEPALIVE_MINUTES, workQueue, new AbortPolicy());
    }

    public Threadpool(String poolName, int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        this(poolName, corePoolSize, maximumPoolSize, DEFAULT_KEEPALIVE_MINUTES, workQueue, handler);
    }

    public Threadpool(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveMinutes, BlockingQueue<Runnable> workQueue,
                             RejectedExecutionHandler handler) {
        this.threadFactory = new SoaThreadFactory(poolName);
        this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveMinutes, TimeUnit.MINUTES, workQueue, threadFactory, handler);
    }

    public static Threadpool newCachedThreadpool(String poolName) {
        return new Threadpool(poolName, 0, Integer.MAX_VALUE, new SynchronousQueue<Runnable>());
    }

    public void execute(Runnable run) {
        this.executorService.execute(run);
    }

    public <T> Future<T> submit(Callable<T> call) {
        return this.executorService.submit(call);
    }

    public Future<?> submit(Runnable run) {
        return this.executorService.submit(run);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }
}
