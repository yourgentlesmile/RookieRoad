package group.xc.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏类 ： 用于实现当一定数量的线程到达barrier的时候再将所有线程统一放行
 * 当有线程到达barrier的时候，如果还未到达放行阈值，则调用到达线程的await方法使其等待
 * 等达到阈值的时候，统一唤醒
 *
 * 与CountDownLatch的区别，CyclicBarrier将所有等待线程放行之后，barrier是会重置的，可已多次使用
 * 而CountDownLacth只能使用一次
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //需要等待5个线程到达再放行
        CyclicBarrier barrier = new CyclicBarrier(5,() -> {
            System.out.println("Over the barrier");
        });
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
