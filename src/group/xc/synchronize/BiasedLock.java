package group.xc.synchronize;

import org.openjdk.jol.info.ClassLayout;

/**
 * 演示偏向锁
 *
 * 初次演示，发现锁标志依旧为00
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           68 f3 a6 18 (01101000 11110011 10100110 00011000) (413594472)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
 *      12     4        (loss due to the next object alignment)
 * Instance size: 16 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 *
 * 这是因为，在java 1.6之后是默认启用的，但在应用程序启动的几秒种(默认是4000)之后才激活，可以使用
 * -XX:BiasedLockingStartupDelay=0 参数关闭，如果确定应用程序中所有锁通常情况下处于
 * 竞争状态，可以通过-XX:-UseBiasedLocking = false 参数关闭偏向锁
 * OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           05 78 e9 18 (00000101 01111000 11101001 00011000) (417953797)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
 *      12     4        (loss due to the next object alignment)
 * Instance size: 16 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 *
 * 可以看到，是否是偏向锁的bit位置为了1
 */
public class BiasedLock {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
class MyThread extends Thread {
    static Object obj = new Object();
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            synchronized (obj) {
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
        }
    }
}