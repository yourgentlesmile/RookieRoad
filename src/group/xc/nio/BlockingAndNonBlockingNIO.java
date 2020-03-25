package group.xc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * 使用NIO完成网络通信的三个核心：
 * 1、通道：负责连接
 * 2、缓冲区：负责数据的存取
 * 3、选择器(Selector)：是SelectableChannel的多路复用器，用于监控SelectableChannel的IO状况
 *
 * java.nio.channels.Channel 接口：
 *   |--SelectableChannel
 *     |--SocketChannel
 *     |--ServerSocketChannel
 *     |--DatagramChannel
 *
 *     |--Pipe.SinkChannel
 *     |--Pipe.SourceChannel
 */
public class BlockingAndNonBlockingNIO {
    /**
     * 阻塞式客户端
     */
    public static void clientBlocked() throws IOException {
        //获取通道
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9000));

        //分配指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);

        //读取本地文件，并发送到客户端
        FileChannel open = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);

        while (open.read(bb) != -1) {
            bb.flip();
            sc.write(bb);
            bb.clear();
        }

        //表示不再有输出
        sc.shutdownOutput();
        open.close();
        sc.close();
    }

    /**
     * 阻塞式服务端
     */
    public static void serverBlocked() throws IOException {
        //获取通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //绑定端口号
        socketChannel.bind(new InetSocketAddress(9000));
        //获取客户端连接的通道
        SocketChannel channel = socketChannel.accept();
        FileChannel file = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ);
        //分配指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);
        while (channel.read(bb) != -1) {
            bb.flip();
            file.write(bb);
            bb.clear();
        }
        channel.close();
        socketChannel.close();
        file.close();
    }

    /**
     * 非阻塞式客户端
     */
    public static void clientNonBlocked() throws IOException {
        //获取通道
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9000));

        //切换成非阻塞模式
        sc.configureBlocking(false);

        //分配指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);

        //读取本地文件，并发送到客户端
        FileChannel open = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);

        while (open.read(bb) != -1) {
            bb.flip();
            sc.write(bb);
            bb.clear();
        }

        //表示不再有输出
        sc.shutdownOutput();
        open.close();
        sc.close();
    }
    /**
     * 阻塞式服务端
     */
    public static void serverNonBlocked() throws IOException {
        //获取通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        //绑定端口号
        socketChannel.bind(new InetSocketAddress(9000));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器,并监听"接收事件"
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //轮询式的获取选择器上已经"准备就绪"的事件
        while (selector.select() > 0) {
            //获取当前选择器中所有注册的"选择键"(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //获取准备"就绪"的事件
                SelectionKey sk = iterator.next();
                //判断具体是什么事件准备就绪
                if(sk.isAcceptable()) {
                    //若"接收就绪"，获取客户端连接
                    SocketChannel accept = socketChannel.accept();
                    //切换非阻塞模式
                    accept.configureBlocking(false);
                    //将该通道注册到选择器上
                    accept.register(selector, SelectionKey.OP_READ);
                }else if(sk.isReadable()) {
                    //获取当前选择器上"读就绪"状态的通道
                    SocketChannel channel = (SocketChannel)sk.channel();
                    FileChannel file = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ);
                    //分配指定大小的缓冲区
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    while (channel.read(bb) != -1) {
                        bb.flip();
                        file.write(bb);
                        bb.clear();
                    }
                }
                //取消选择键
                iterator.remove();
            }
        }
        //获取客户端连接的通道
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        serverNonBlocked();
    }
}
