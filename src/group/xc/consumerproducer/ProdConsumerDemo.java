package group.xc.consumerproducer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 覆盖知识点
 *  volatile / CAS / AtomicInteger / BlockQueue / 线程交互 / 原子引用
 */
class Mysource {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    BlockingQueue<String> blockingQueue = null;

    public Mysource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    public void myProd() throws InterruptedException {
        String data = null;
        while (FLAG) {
            boolean retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + String.format("input queue %s success",data));
            }else {
                System.out.println(Thread.currentThread().getName() + String.format("input queue %s failed",data));
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + String.format("  interrupt produece FLAG = %s",FLAG));
    }
    public void myComsumer() throws InterruptedException {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);
            if(null == result) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + " already wait over 2 second,exit now");
                return ;
            }
        }
    }
}
public class ProdConsumerDemo {
    public static void main(String[] args) {
        Mysource mysource = new Mysource(new ArrayBlockingQueue<String>(10));
    }
}
