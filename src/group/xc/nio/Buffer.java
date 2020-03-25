package group.xc.nio;

import java.nio.ByteBuffer;

/**
 * Buffer负责数据的存取。缓冲区就是数组，用于存储不同数据类型的数据
 *
 * 根据数据类型不同(boolean除外)，提供了相应类型的缓冲区
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 *
 * 缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区
 * get() : 获取缓冲区中的数据
 *
 * buffer中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量,一旦声明就不能改变
 * limit : 界限，表示缓冲区中可以操作数据的大小.(就是limit后面的数据不能进行读写)
 * position : 位置，表示缓冲区中正在操作数据的位置
 * mark : 标记，表示记录当前position位置。可以通过reset() 恢复到mark的位置
 * mark <= position <= limit <= capacity
 */
public class Buffer {
    public static void operateBuffer() {
        String data = "abcde";
        // 分配指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);

        System.out.println("-----allocate()-----");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //存入数据
        bb.put(data.getBytes());

        System.out.println("-----put()-----");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //切换读取模式
        bb.flip();
        System.out.println("-----flip()-----");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        byte[] bytes = new byte[bb.limit()];
        bb.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println("-----get()-----");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //rewind调用后，就可以重复读数据了
        bb.rewind();
        System.out.println("-----rewind()-----");
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //清空缓冲区，里面的数据并没有被清除，只是指针被复位了
        bb.clear();

        bb.mark();
        bb.reset();
        //获取缓冲区中的剩余的数据数量
        int remaining = bb.remaining();
    }
    public static void directBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.isDirect();
    }
    public static void main(String[] args) {

    }
}
