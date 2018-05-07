package me.duzhi.demo.locks;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileNoAtomic extends Thread {
    public static AtomicInteger count = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 2000; i++) {
            count.incrementAndGet();
        }
        System.out.println(getId() + ":" + count.get());
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileNoAtomic[] volatileNoAtomics = new VolatileNoAtomic[10];
        for (int i = 0; i < volatileNoAtomics.length; i++) {
            volatileNoAtomics[i] = new VolatileNoAtomic();
        }
        // Thread.sleep(1000);

        for (VolatileNoAtomic volatileNoAtomic : volatileNoAtomics) {
            volatileNoAtomic.start();
        }
        Thread.sleep(1000);
        System.out.println("count:" + count.get());
    }
}
