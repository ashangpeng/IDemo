package me.duzhi.demo.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectedHandlerTest {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
        //主线程执行
        // ((ThreadPoolExecutor) executorService).setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //用于被拒绝任务的处理程序，它将抛出 RejectedExecutionException.
        //((ThreadPoolExecutor) executorService).setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //  ((ThreadPoolExecutor) executorService).setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        ((ThreadPoolExecutor) executorService).setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            System.out.println("execute：" + i);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("threadName:" + Thread.currentThread().getName() + ":" + finalI);
                    try {
                        Thread.sleep(1000);
                        System.out.println("yesy：" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
