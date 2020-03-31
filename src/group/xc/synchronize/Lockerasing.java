package group.xc.synchronize;

/**
 * 锁消除演示
 */
public class Lockerasing {
    public static String ccString(String s1, String s2, String s3) {
        return new StringBuffer().append(s1).append(s2).append(s3).toString();
    }
    public static void main(String[] args) {
        ccString("aa","bb","cc");
    }
}
