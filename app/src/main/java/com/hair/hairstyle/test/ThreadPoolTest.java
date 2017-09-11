package com.hair.hairstyle.test;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yunshan on 17/9/5.
 */

public class ThreadPoolTest {

    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            fixedThreadPool.execute(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔2秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程：" + threadName + ",正在执行");
        }, 1, 2, TimeUnit.SECONDS);


        ExecutorService priorityThreadPool = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<>());
        for (int i = 1; i <= 10; i++) {
            final int priority = i;
            priorityThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程：" + threadName + ",正在执行优先级为：" + priority + "的任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
