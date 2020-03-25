package group.xc.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.SortedMap;

/**
 * 用于源节点与目标节点的连接，在Java NIO中负责缓冲区中数据的传输。Channel本身
 * 不存储数据，因此需要配合缓冲区进行传输
 */
public class Channel {
    /**
     * 字符集编码与解码
     */
    public static void charSet() throws CharacterCodingException {
        //获取可用的全部字符集
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        Charset charset = StandardCharsets.UTF_8;
        CharsetEncoder ce = charset.newEncoder();
        CharsetDecoder cd = charset.newDecoder();
        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("hello world");
        cBuf.flip();
        ByteBuffer encode = ce.encode(cBuf);
        //这样就获得了GBK编码的byteBuffer


        encode.flip();
        //这样就把字节解码成字符
        CharBuffer decode = cd.decode(encode);
    }
    /**
     * 分散(Scatter)与聚集(Gather)
     *
     * 分散读取(Scattering Read) :  将通道中的数据分散到多个缓冲区中
     * 聚集写入(Gathering Writes) : 将多个缓冲区中的数据聚集到通道中
     */
    public static void scatterAndGather() throws IOException {
        RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel = file.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer bb1 = ByteBuffer.allocate(100);
        ByteBuffer bb2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] bufs = {bb1, bb2};
        channel.read(bufs);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        RandomAccessFile file2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = file2.getChannel();

        //聚集写入
        channel2.write(bufs);
        channel.close();
        channel2.close();
    }
    /**
     * 通道之间的数据传输(直接缓冲区)
     * transferFrom
     * transferTo
     */
    public static void readFileByChannelDirect() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            //StandardOpenOption.CREATE_NEW ,如果不存在则创建，如果存在则报错
            //StandardOpenOption.CREATE ,如果不存在则创建，如果存在则覆盖
            outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            inChannel.transferTo(0, inChannel.size(), outChannel);
            // 或者是
            //outChannel.transferFrom(inChannel, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 使用直接缓冲区完成文件的复制(内存映射文件)
     *
     * 注意，内存映射文件只有ByteBuffer支持
     */
    public static void readFileByChannelMemoryMappered() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            //StandardOpenOption.CREATE_NEW ,如果不存在则创建，如果存在则报错
            //StandardOpenOption.CREATE ,如果不存在则创建，如果存在则覆盖
            outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            //此处的map的模式要与通道的模式相匹配
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
            //直接对缓冲区进行数据的读写操作
            byte[] bytes = new byte[inMappedBuf.limit()];
            outMappedBuf.put(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 利用通道完成文件的复制(非直接缓冲区)
     */
    public static void readFileByChannel() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jps");
            //获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            //分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            while (inChannel.read(buf) != -1) {
                //切换读取数据的模式
                buf.flip();
                //将缓冲区的数据写入通道
                outChannel.write(buf);
                //清空缓冲区
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
