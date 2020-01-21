package group.xc.oom;

import java.util.Random;

/**
 * todo
 *
 * @version V1.0
 * @Author XiongCheng
 * @Date 2020/1/21 10:32.
 */
public class JavaHeapSpace {
    /**
     * -Xms10m -Xmx10m
     * @param args
     */
    public static void main(String[] args) {
        byte[] arr = new byte[80 * 1024 *1024];
    }
}
