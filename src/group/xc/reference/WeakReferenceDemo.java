package group.xc.reference;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> softRef = new WeakReference(o1);
        System.out.println(o1);
        System.out.println(softRef.get());
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(softRef.get());
    }
    public static void weakHashMap() {
        WeakHashMap<Integer,String> map = new WeakHashMap<>();
        Integer key = 1;
        String value = "s";
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t" + map.size());
    }
}
