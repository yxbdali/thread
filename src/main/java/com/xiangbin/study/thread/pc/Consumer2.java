package com.xiangbin.study.thread.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangbin.yang
 * @since 2017/10/9
 */
public class Consumer2 implements Runnable {
    private ShareData shareData;
    private ReentrantLock lock;
    private Condition condition;

    private Consumer2(ShareData shareData, ReentrantLock lock, Condition condition) {
        this.shareData = shareData;
        this.lock = lock;
        this.condition = condition;
    }

    public static Consumer2 of(ShareData shareData, ReentrantLock lock, Condition condition) {
        return new Consumer2(shareData, lock, condition);
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (!shareData.isAvaliable()) {
                condition.await();
            }
            System.out.println(String.format("Consumer2 get data: %s", shareData.getData()));
            shareData.setAvaliable(false);
            condition.signal();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
