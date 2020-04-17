package group.xc.exercise.storage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务端
 *
 * @Date 2020/4/17 10:27.
 */
public class Server {
    final int port;
    ExecutorService threadPool;
    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress(port));
        while (true) {
            Socket accept = socket.accept();
            receive(accept);
        }
    }
    private void receive(Socket socket) {
        threadPool.submit(new HandleService(socket));
    }
    private void init() {
        threadPool = new ThreadPoolExecutor(2, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(20));
    }

    public static void main(String[] args) {
        Server server = new Server(8888);
        server.init();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
