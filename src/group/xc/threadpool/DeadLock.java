package group.xc.threadpool;

import java.util.concurrent.TimeUnit;

/**
 * 死锁的定义：
 * 死锁是指两个或两个以上的进程在执行过程中
 * 因为争夺资源而造成的一种互相等待的现象
 * 若无外力干涉那它们都将无法推进下去
 *
 * 产生死锁的主要原因
 * 1、系统资源不足
 * 2、进程运行推进的顺序不合理
 * 3、资源分配不当
 *
 *
 */
class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有: " + lockA + "尝试获得: " + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有: " + lockB + "尝试获得: " + lockA);
            }
        }
    }
}
public class DeadLock {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();
        /**
         * 使用jstack发现死锁
         * Found one Java-level deadlock:
         * =============================
         * "ThreadBBB":
         *   waiting to lock monitor 0x000000001cb81478 (object 0x000000076b28b748, a java.lang.String),
         *   which is held by "ThreadAAA"
         * "ThreadAAA":
         *   waiting to lock monitor 0x000000001cb7ed48 (object 0x000000076b28b780, a java.lang.String),
         *   which is held by "ThreadBBB"
         *
         * Java stack information for the threads listed above:
         * ===================================================
         * "ThreadBBB":
         *         at group.xc.threadpool.HoldLockThread.run(DeadLock.java:37)
         *         - waiting to lock <0x000000076b28b748> (a java.lang.String)
         *         - locked <0x000000076b28b780> (a java.lang.String)
         *         at java.lang.Thread.run(Thread.java:748)
         * "ThreadAAA":
         *         at group.xc.threadpool.HoldLockThread.run(DeadLock.java:37)
         *         - waiting to lock <0x000000076b28b780> (a java.lang.String)
         *         - locked <0x000000076b28b748> (a java.lang.String)
         *         at java.lang.Thread.run(Thread.java:748)
         *
         * Found 1 deadlock.
         */
    }
}
