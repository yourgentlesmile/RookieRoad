package group.xc.exercise.storage;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局变量类
 *
 * @Date 2020/4/17 11:32.
 */
public class Context {
    private static class singtonBlock {
        private static final StorageService store = new StorageService();
        private static final AtomicLong seq = new AtomicLong(0);
    }
    public static StorageService getStorage() {
        return singtonBlock.store;
    }
    public static long getSeq() {
        return singtonBlock.seq.incrementAndGet();
    }

}
