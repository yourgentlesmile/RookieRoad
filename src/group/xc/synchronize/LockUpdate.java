package group.xc.synchronize;

/**
 * 锁粗化
 */
public class LockUpdate {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            sb.append("aa");
        }
    }
}
