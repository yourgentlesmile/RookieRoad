package group.xc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：
 * 对于同一个资源，多线程读的时候可以不必要进行加锁
 * 写时必须加锁，只能有一个线程对资源进行写操作
 * 读-读能共存
 * 读-写不能共存
 * 写-写不能共存
 * 使用ReentrantReadWriteLock进行读写控制
 */
public class ReadWriteLock {
    Map<Integer,Integer> source = new HashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public void put(int a,int b){
        lock.writeLock().lock();
        try {
            source.put(a, b);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
    public int get(int key) {
        lock.readLock().lock();
        try {
            return source.get(key);
        } catch (Exception e){
            e.printStackTrace();

        } finally {
            lock.readLock().unlock();
        }
        return -1;
    }
}
