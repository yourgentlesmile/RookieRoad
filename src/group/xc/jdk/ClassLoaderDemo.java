package group.xc.jdk;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        Object o = new Object();
        ClassLoaderDemo d = new ClassLoaderDemo();
        System.out.println(o.getClass().getClassLoader());
        System.out.println(d.getClass().getClassLoader());
        System.out.println(d.getClass().getClassLoader().getParent());
        System.out.println(d.getClass().getClassLoader().getParent().getParent());
    }
}
