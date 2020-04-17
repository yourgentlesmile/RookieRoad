package group.xc.exercise.storage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局并发安全的数据存储
 *
 * @Date 2020/4/17 10:47.
 */
public class StorageService {
    private AtomicInteger userNum = new AtomicInteger(0);
    private AtomicLong msgNum = new AtomicLong(0);
    private ConcurrentHashMap<String, CopyOnWriteArrayList<Message>> data = new ConcurrentHashMap<>();
    public void store(Message value) {
        if (!data.containsKey(value.getUser())) {
            data.put(value.getUser(), new CopyOnWriteArrayList<>());
            userNum.incrementAndGet();
        }
        CopyOnWriteArrayList<Message> v = data.get(value.getUser());
        v.add(value);
        msgNum.incrementAndGet();
    }
    public ArrayList<Message> query(String user) {
        CopyOnWriteArrayList<Message> messages = data.get(user);
        return messages == null ? new ArrayList<>() : new ArrayList<>(messages);
    }
    public int delete(Message v, boolean truncate) {
        CopyOnWriteArrayList<Message> remove = data.remove(v.getUser());
        if (remove == null) {
            return 0;
        }
        if(truncate) {
            msgNum.addAndGet(remove.size() * -1);
            userNum.decrementAndGet();
            data.remove(v.getUser());
            return 1;
        }
        remove.remove(v);
        return 1;
    }
}
