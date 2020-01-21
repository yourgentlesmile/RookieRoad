package group.xc.oom;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class GCOverheadLimitExceeded {
    /**
     * JVM参数 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
     * @param args
     */
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        while (true) {
            list.add(String.valueOf(++i).intern());
        }
    }
}
