package group.xc.jvm;

public class GCRootDemo {
    private int _1MB = 1 * 1024 * 1024;
    private byte[] bytesArray = new byte[100 * _1MB];

    public static void main(String[] args) {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
    }
}