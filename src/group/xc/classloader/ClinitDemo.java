package group.xc.classloader;

/**
 * 此用例用于验证，clinit是线程安全的
 * 结果是，"clinit 开始初始化"这句只会被打印一次，就表明clinit是线程安全的
 */
public class ClinitDemo {
    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + "开始");
            DeadThread d = new DeadThread();
            System.out.println(Thread.currentThread().getName() + "结束");
        };
        Thread a = new Thread((r));
        Thread b = new Thread((r));
        a.start();
        b.start();
    }
}
class DeadThread {
    static{
        if(true) {
            System.out.println("clinit 开始初始化");
            while (true){

            }
        }
    }
}