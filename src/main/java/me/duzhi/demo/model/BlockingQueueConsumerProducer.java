package me.duzhi.demo.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueConsumerProducer {
    public static void main(String[] args) {
        Resource resource = new Resource();
        Thread thread = new ProducerThread3(resource);
        Thread thread1 = new ConsumerThread3(resource);
        Thread thread2 = new ConsumerThread3(resource);
        thread.start();
        thread1.start();
        thread2.start();
    }
    //消费者线程
    static class ConsumerThread3 extends Thread {
        Resource resource;
        public ConsumerThread3(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long) (1000 * Math.random()));
                    resource.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //生产者线程
    static class ProducerThread3 extends Thread {
        Resource resource;

        public ProducerThread3(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long) (1000 * Math.random()));
                    resource.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class Resource {
        BlockingQueue<Integer> integers = new LinkedBlockingDeque<Integer>();

        public void add() throws InterruptedException {
            integers.put(1);
            System.out.println("生产者" + Thread.currentThread().getName()
                    + "生产一件资源," + "当前资源池有" + integers.size() +
                    "个资源");

        }

        public void remove() throws InterruptedException {
            int i = integers.take();
            System.out.println("消费者" + Thread.currentThread().getName()
                    + "处理了一件资源," + "当前资源池有" + integers.size() +
                    "个资源");

        }
    }

}
