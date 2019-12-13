package group.xc.lock;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 用于某个线程需要等待其他一定数量的线程干完某些事之后再继续执行
 *
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        //需要等待5个线程执行完，才能继续执行
        CountDownLatch trigger = new CountDownLatch(5);
        try {
            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    System.out.println("I'm finish -> " + Thread.currentThread().getName());
                    trigger.countDown();
                },String.valueOf(i)).start();
            }
            trigger.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main finish.");
    }
}
