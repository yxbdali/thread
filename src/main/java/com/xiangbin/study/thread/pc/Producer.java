package com.xiangbin.study.thread.pc;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangbin.yang
 * @since 2017/10/9
 */
@Slf4j
public class Producer implements Runnable {
    private ShareData data;
    private Object lock;

    private Producer(ShareData data, Object lock) {
        this.data = data;
        this.lock = lock;
    }

    public static Producer of(ShareData data, Object lock) {
        return new Producer(data, lock);
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (data.isAvaliable()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String _data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            data.setData(_data);
            data.setAvaliable(true);
            System.out.println(String.format("Producer Set data: %s", _data));
            lock.notify();
        }
    }
}
