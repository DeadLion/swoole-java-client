package com.chelun.soa.thread;

import java.io.Closeable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chelun.soa.Constants;

public class SoaThreadFactory implements ThreadFactory, Closeable{
	private static final Logger                         logger      = LoggerFactory.getLogger(SoaThreadFactory.class);

    private static ThreadGroup                          threadGroup = new ThreadGroup(Constants.THREAD_GROUP_NAME);

    private String                                      prefix;

    private List<WeakReference<Thread>>                 threadList  = Collections.synchronizedList(new ArrayList<WeakReference<Thread>>());

    private static ConcurrentMap<String, AtomicInteger> prefixToSeq = new ConcurrentHashMap<String, AtomicInteger>();

    public SoaThreadFactory(String namePrefix) {
        if (namePrefix != null) {
            this.prefix = namePrefix;
        } else {
            this.prefix = Constants.THREAD_DEFAULT_PREFIX;
        }
    }

    public SoaThreadFactory() {
        this(null);
    }

    public static ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    @Override
    public Thread newThread(Runnable r) {
        return newThread(r, "");
    }

    public Thread newThread(Runnable r, String threadNamePrefix) {
        String threadPrefix = prefix + threadNamePrefix;
        prefixToSeq.putIfAbsent(threadPrefix, new AtomicInteger(1));
        Thread thread = new Thread(threadGroup, r, threadPrefix + prefixToSeq.get(threadPrefix).getAndIncrement());
        thread.setDaemon(true);
        threadList.add(new WeakReference<Thread>(thread));
        return thread;
    }

    @Override
    public void close() {
        for (WeakReference<Thread> ref : threadList) {
            closeThread(ref.get());
        }
    }

    public synchronized static void closeAll() {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        int actualCount = threadGroup.enumerate(threads, false);
        for (int i = 0; i < actualCount; i++) {
            closeThread(threads[i]);
        }
    }

    private static void closeThread(Thread thread) {
        if (thread != null && thread.isAlive()) {
            if (thread instanceof Closeable) {
                try {
                    ((Closeable) thread).close();
                } catch (Exception e) {
                    logger.error("Unexpected exception occured while close pegasus thread.", e);
                }
            }
            thread.interrupt();
        }
    }
}
