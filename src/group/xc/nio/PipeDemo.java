package group.xc.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * todo
 *
 * @version V1.0
 * @Author XiongCheng
 * @Date 2020/3/25 21:41.
 */
public class PipeDemo {
    /**
     * 可以用于一个线程向另外一个线程发送数据
     * Pipe是一个单向的管道
     * @throws IOException
     */
    public static void usingPipe() throws IOException {
        //1.获取管道
        Pipe pipe= Pipe.open();
        //2.将缓冲区中的数据写入管道
        ByteBuffer buf=ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel=pipe.sink();
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //3.读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel=pipe.source();
        buf.flip();
        int len=sourceChannel.read(buf);
        System.out.println(new String(buf.array(),0,len));

        sourceChannel.close();
        sinkChannel.close();
    }
}
