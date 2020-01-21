package group.xc.oom;

import java.nio.ByteBuffer;

/**
 *
 */
public class DirectBufferMemory {
    /**
     * JVM参数 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocateDirect(6* 1024 * 2014);
    }
}
