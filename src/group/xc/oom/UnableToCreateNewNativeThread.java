package group.xc.oom;

/**
 * -Xms5m -Xmx5m -Xss256k
 */
public class UnableToCreateNewNativeThread {
    public static void main(String[] args) {
        byte[] arr = new byte[5 * 900 * 1000];
        int i = 0;
        while(true) {
            System.out.println("-==-=-==-=-=" + i);
            i++;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
