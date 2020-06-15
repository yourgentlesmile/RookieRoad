package group.xc.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        final AtomicInteger ai = new AtomicInteger();
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> ai.getAndIncrement());
        new Thread(() -> {
            System.out.println("Current thread is " + tl.get());
        }).start();
        new Thread(() -> {
            System.out.println("Current thread is " + tl.get());
        }).start();
        new Thread(() -> {
            System.out.println("Current thread is " + tl.get());
        }).start();
        new Thread(() -> {
            System.out.println("Current thread is " + tl.get());
        }).start();
        new Thread(() -> {
            System.out.println("Current thread is " + tl.get());
        }).start();
        new Thread(() -> {
            System.out.println("Current thread is " + tl.get());
        }).start();
    }
}
