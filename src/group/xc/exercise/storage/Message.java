package group.xc.exercise.storage;

import java.util.Objects;

/**
 * 消息实体类
 *
 * @Date 2020/4/17 11:00.
 */
public class Message {
    final private String user;
    final private String value;
    final private Long createTime;
    final private String addr;
    final private long sequenceNumber;

    public Message(String user, String value, Long createTime, String addr, long sequenceNumber) {
        this.user = user;
        this.value = value;
        this.createTime = createTime;
        this.addr = addr;
        this.sequenceNumber = sequenceNumber;
    }

    public Message(long sequenceNumber) {
        this.user = null;
        this.value = null;
        this.createTime = null;
        this.addr = null;
        this.sequenceNumber = sequenceNumber;
    }

    public String getUser() {
        return user;
    }

    public String getValue() {
        return value;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getAddr() {
        return addr;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return sequenceNumber == message.sequenceNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, value, createTime, addr, sequenceNumber);
    }

    @Override
    public String toString() {
        return "Message{" +
        "user='" + user + '\'' +
        ", value='" + value + '\'' +
        ", createTime=" + createTime +
        ", addr='" + addr + '\'' +
        ", sequenceNumber=" + sequenceNumber +
        '}';
    }
}
