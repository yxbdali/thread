package com.xiangbin.study.thread.pc;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangbin.yang
 * @since 2017/10/9
 */
@Slf4j
public class Consumer implements Runnable {
    private ShareData data;
    private Object lock;

    private Consumer(ShareData data, Object lock) {
        this.data = data;
        this.lock = lock;
    }

    public static Consumer of(ShareData data, Object lock) {
        return new Consumer(data, lock);
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (!data.isAvaliable()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(String.format("Consumer get data: %s", data.getData()));
            data.setAvaliable(false);
            lock.notify();
        }
    }
}
