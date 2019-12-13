package group.xc.blockingqueue;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * 阻塞队列：
 * 当队列为空的时候从队列中获取元素的动作将会被阻塞
 * 当队列满的时候从队列中添加元素的动作将会被阻塞
 * 阻塞队列有以下7种类型：
 * 1、ArrayBlockingQueue: 由数据结构组成的有界阻塞队列
 * 2、LinkedBlockingQueue: 由链表结构组成的有界(但大小默认值为Integer.MAX_VALUE)阻塞队列
 * 3、PriorityBlockingQueue: 支持优先级排序的无界阻塞队列
 * 4、DelayQueue: 使用优先级队列实现的延迟无界阻塞队列
 * 5、SynchronousQueue:不储存元素的阻塞队列，也即单个元素的队列
 * 6、LinkedTransferQueue: 由链表结构组成的无界阻塞队列
 * 7、LinkedBlockingDeque: 由链表结构组成的双向阻塞队列
 *
 * 操作方法分为：
 * 1、方法类型    抛出异常     特殊值       阻塞         超时
 * 2、插入         add(e)     offer(e)     put()        offer(e,time,unit)
 * 2、移除         remove()   poll()       take()       poll(time,unit)
 * 2、检查        element(e)  peek()       NO           NO
 *
 * 抛出异常：
 * 当阻塞队列满时，再往队列里add元素，会抛出IllegalStateException: Queue full
 * 当阻塞队列空时，再往队列里remove元素会抛NoSuchElementException
 *
 * 特殊值:
 * 插入方法，成功true 失败 false
 * 移除方法：成功返回出队的元素，队列里面没有就返回null
 *
 * 阻塞：
 * 当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞生产线程直到put数据成功或响应中断退出
 * 当阻塞队列空时，消费者线程视图从队列里take元素，队列一直会阻塞消费线程，直到队列可用
 *
 * 超时:
 * 当阻塞队列满时，队列会阻塞生产者线程一定时间，超过限时后，生产者线程就会退出阻塞并返回false
 *
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(1);
    }
}
