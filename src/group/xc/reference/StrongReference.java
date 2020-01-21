package group.xc.reference;

/**
 * 当内存不足时，JVM开始垃圾回收，对于强引用的对象，就算是出现了OOM也不会对该对象进行回收，死都不收
 */
public class StrongReference {
    public static void main(String[] args) {
        Object obj = new Object(); //这种定义默认就是强引用
        Object obj2 = obj;
        obj = null;
        System.gc();
        System.out.println(obj2);
    }
}
