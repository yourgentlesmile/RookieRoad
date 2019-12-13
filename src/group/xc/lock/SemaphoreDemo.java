package group.xc.lock;

import java.util.concurrent.Semaphore;

/**
 * 信号量：
 * 主要是两个目的
 * 1.一个是用于多个共享资源的互斥使用
 * 2.用于并发线程数的控制
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //3个槽位
        //还有一个构造参数 public Semaphore(int permits, boolean fair)
        //第二个参数表示是否使用公平锁 ，默认使用非公平锁
        Semaphore slot = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    slot.acquire();
                    System.out.println("Hold 1 slot");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("release 1 slot");
                  slot.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
