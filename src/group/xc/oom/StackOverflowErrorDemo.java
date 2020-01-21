package group.xc.oom;

/**
 * todo
 *
 * @version V1.0
 * @Author XiongCheng
 * @Date 2020/1/21 10:32.
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        overflow();
    }
    public static void overflow() {
        overflow();
    }
}
