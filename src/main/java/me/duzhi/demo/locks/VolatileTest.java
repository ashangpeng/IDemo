package me.duzhi.demo.locks;

public class VolatileTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadVolatile threadVolatile = new ThreadVolatile();

        threadVolatile.start();
        Thread.sleep(1000);
        threadVolatile.stop(false);
        System.out.println("thread:00");
        Thread.sleep(1000);
        System.out.println("thread:00" + threadVolatile.flag);
    }

    public static class ThreadVolatile extends Thread {
        public volatile
        boolean flag = true;

        @Override
        public void run() {
            System.out.println("start");
            while (flag) {
            }
            System.out.println("end");
        }

        public void stop(boolean flag) {
            this.flag = flag;
        }
    }

}
