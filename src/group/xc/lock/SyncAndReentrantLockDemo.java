package group.xc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 和 lock 的区别，用新的lock有什么好处
 *
 * 1、synchronized是关键字，属于jvm层面
 * 通过monitorenter和monitorexit
 * monitorenter(底层是通过monitor对象来完成的，其实wait/notify等方法也依赖于monitor对象，只有在同步块或方法
 * 中才能调wait/notify等方法)
 *
 * lock是具体类(java.util.concurrent.locks.lock)，是api层面的锁
 *
 * 2、使用方法：
 * synchronized 不需要用户手动去释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
 * ReentrantLock需要用户手动释放锁，若没有主动释放锁，就有可能导致出现死锁的现象
 *
 * 3、等待是否可以中断
 * synchronized 不可中断，除非抛出异常或者正常运行完成
 * ReentrantLock 可中断 1、设置超时方法tryLock(long timeout,TimeUnit unit)
 *                      2、LockInterruptibly()放代码块中，调用interrupt()方法可中断
 * 4、加锁是否公平
 * synchronized非公平锁
 * ReentrantLock两者都可以，默认非公平锁，构造方法可以传入boolean值，true：公平, false非公平
 *
 * 5、锁绑定多个条件Condition
 * synchronized 没有
 * ReentrantLock用来实现分组唤醒需要唤醒的线程们。可以精确唤醒，而不是像synchronized要么随机唤醒一个线程，要么唤醒全部线程
 *
 */
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        condition1.await();  //此线程在condition1下等待
        condition1.signal(); //唤醒所有在condition1下等待的线程
    }
}
