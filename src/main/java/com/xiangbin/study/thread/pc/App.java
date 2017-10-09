package com.xiangbin.study.thread.pc;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String LOCK = "";
    private static ShareData shareData = new ShareData();
    private static ShareData shareData2 = new ShareData();
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main( String[] args ) throws InterruptedException, IOException {
        pc2();
        pc();
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void pc() throws InterruptedException {
        executorService.submit(Consumer.of(shareData, LOCK));
        Thread.sleep(1000);
        executorService.submit(Producer.of(shareData, LOCK));
    }

    private static void pc2() throws InterruptedException {
        executorService.submit(Consumer2.of(shareData2, lock, condition));
        Thread.sleep(3000);
        executorService.submit(Producer2.of(shareData2, lock, condition));
    }
}
