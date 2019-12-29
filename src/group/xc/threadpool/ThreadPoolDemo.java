package group.xc.threadpool;

import java.util.concurrent.Executors;

/**
 * 线程池做的工作主要是控制运行的线程的数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务
 * 如果线程数量超过了最大数量，超出数量的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行
 *
 * 主要特点：
 * 1、线程复用
 * 2、控制最大并发数
 * 3、管理线程
 *
 * 好处：
 * 1、降低资源消耗，通过重复利用已创建的线程，降低线程创建和销毁造成的消耗
 * 2、提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行
 * 3、提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源
 * 还会降低系统的稳定性，使用线程池可以进行统一分配，调优和监控
 *
 *
 * 线程池有以下几种，
 * Executors.newScheduledThreadPool()
 *
 * Executors.newFixedThreadPool() 适用于执行长期任务，性能好很多
 * 创建一个定长的线程池，使用LinkedBlockingQueue作为阻塞队列的实现，可控制线程的最大并发数，超出的任务会在队列中等待，
 *
 * Executors.newCachedThreadPool() 适用于执行很多短期异步的小程序或者负载较轻的服务器
 * 创建一个可缓存线程池，阻塞队列使用SynchronousQueue,如果线程池长度超过了处理需要，可灵活回收空闲线程，若无可回收则创建新线程
 * 它的corePoolSize设置为0，maximumPoolSize设置为Int最大值2147483647
 *
 * Executors.newSingleThreadExecutor() 一个一个任务执行的场景
 * 创建单线程化的线程池，使用LinkedBlockingQueue作为阻塞队列的实现，它只会用唯一的工作线程来执行任务，保证所有任务都按指定顺序执行
 *
 * java8新增：使用目前机器上可用的处理器作为它的并行级别
 * Executors.newWorkStealingPool()
 */

/**
 * 线程池的7个重要参数
 * corePoolSize:
 * 线程池中常驻核心线程数
 * maximumPoolSize:
 * 线程池能够容纳同时执行的最大线程数
 * keepAliveTime:
 * 多余的空闲线程存活时间，当空闲时间达到keepAliveTime值时，多余的线程会被销毁直到只剩下corePoolSize个线程为止
 * unit: keepAliveTime的单位
 * workQueue: 任务队列，被提交但尚未被执行的任务
 * threadFactory: 表示生成线程池中工作线程的线程工厂，用于创建线程，一般用默认的即可
 * handler: 拒绝策略，表示当队列满了并且工作线程大于等于线程池的最大线程数(maximumPoolSize)时如何来拒绝
 */

/**
 * JDK内置的4种拒绝策略:
 * AbortPolicy(默认):直接抛出RejectedException异常组织系统正常运行
 * CallerRunPolicy:"调用者运行"一种调节机制，该策略既不会抛弃任务，也不会抛出异常,而是将某些任务回退到调用者，由调用者所在的线程进行执行
 * 也就是说会调用当前线程池的所在的线程去执行被拒绝的任务
 *
 * DiscardOldestPolicy: 抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务
 * DiscardPolicy:直接丢弃任务，不予任何处理也不抛出异常。如果允许任务丢失，这是最好的一种方案
 *
 * 上面4种策略都实现了RejectedExecutionHandler接口
 *
 *
 * 注意：
 * 在实际场景中，不会直接使用Executors创建线程池，因为用它创建出来的Fix和Cache线程池，默认使用的阻塞队列都为
 * LinkedBlockingQueue，而此队列的最大是int的最大值，会导致请求大量积压，从而出现OOM
 *
 *
 *
 * 线程数的设置：
 * 大致分为两种场景
 * 1、CPU密集型
 * CPU密集的意思是该任务需要大量的运算，而没有阻塞，CPU一直全速运行，CPU密集任务只有在真正的多核CPU上才可能得到加速
 * 2、IO密集型
 * 即该任务需要大量的IO，即大量的阻塞
 * 在单线程上运行IO密集型的任务呀会导致浪费大量的CPU运算能力在等待
 * 所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上
 * 这种加速主要就是利用了被浪费掉的阻塞时间
 *
 * CPU密集型任务配置尽可能少的线程数，因此一般设置为 ->  CPU核数 + 1 个线程的线程池
 * IO密集型任务并不是一直在执行任务，由于大部分线程都阻塞，故需要多配置线程数
 * 参考公式：CPU核数/(1 - 阻塞系数)    阻塞系数在0.8~0.9之间
 * 比如8核CPU：8 / (1 - 0.9) = 80个线程
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        Executors.newCachedThreadPool();
    }
}
