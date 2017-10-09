package com.xiangbin.study.thread.pc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangbin.yang
 * @since 2017/10/9
 */
public class Producer2 implements Runnable {
    private ShareData shareData;
    private ReentrantLock lock;
    private Condition condition;

    private Producer2(ShareData shareData, ReentrantLock lock, Condition condition) {
        this.lock = lock;
        this.shareData = shareData;
        this.condition = condition;
    }

    public static Producer2 of(ShareData shareData, ReentrantLock lock, Condition condition) {
        return new Producer2(shareData, lock, condition);
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (shareData.isAvaliable()) {
                condition.await();
            }
            String _data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            shareData.setData(_data);
            shareData.setAvaliable(true);
            System.out.println(String.format("Producer2 set data: %s", _data));
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
