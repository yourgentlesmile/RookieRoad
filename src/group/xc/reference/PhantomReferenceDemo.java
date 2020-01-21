package group.xc.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 相当于一个AOP的后置通知
 *
 *
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantom = new PhantomReference(o1,queue);
        System.out.println(o1);
        System.out.println(phantom.get());
        System.out.println(queue.poll());
    }

    /**
     * 当传入了引用队列这参数的时候，弱引用被回收时，会将对象放入到引用队列当中
     * @throws InterruptedException
     */
    public static void referenceQueue() throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> weak = new WeakReference(o1,queue);
        System.out.println(o1);
        System.out.println(weak.get());
        System.out.println(queue.poll());
        System.out.println("-=-=-=-=-=-=-=-=-=-=-");
        o1 = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(o1);
        System.out.println(weak.get());
        System.out.println(queue.poll());
    }
}
