package group.xc.reference;

import java.lang.ref.SoftReference;

/**
 * 软引用示例
 */
public class SoftReferenceDemo {
    public static void soft_memory_enough() {
        Object o1 = new Object();
        SoftReference<Object> softRef = new SoftReference(o1);
        System.out.println(o1);
        System.out.println(softRef.get());
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(softRef.get());
    }

    /**
     * JVM要配置少一点的内存，同时故意产生大对象，从而创造内存不足的情况，再看看软引用的情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetail
     */
    public static void soft_memory_insufficient() {
        Object o1 = new Object();
        SoftReference<Object> softRef = new SoftReference(o1);
        System.out.println(o1);
        System.out.println(softRef.get());
        o1 = null;
        try {
            /**
             * jvm内存只分了5M，但是却分配了30M的字节数组，必定会GC并引发OOM
             */
            byte[] ar = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softRef.get());
        }
    }
}
