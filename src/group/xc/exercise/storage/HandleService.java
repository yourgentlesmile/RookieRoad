package group.xc.exercise.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * 服务端线程处理
 *
 * @Date 2020/4/17 11:43.
 */
public class HandleService implements Runnable{
    private String username;
    final Socket socket;
    private BufferedReader inputStream;
    private PrintStream outputStream;
    private StorageService storage;
    int currentMode = -1;
    public HandleService(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage = Context.getStorage();
    }

    @Override
    public void run() {
        setUsername();
        while (currentMode != -2 && !socket.isClosed()) {
            switch (currentMode) {
                case 0 :
                    storage();
                    break;
                case 1 :
                    query();
                    break;
                case 2 :
                    delete();
                    break;
                default:
                    currentMode = -1;
            }
            requestMode();
        }
        try {
            outputStream.println("goodbye");
            
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void requestMode() {
        try {
            outputStream.println("请选择模式: storage,query,delete,退出quit");
            
            String s = inputStream.readLine();
            switch (s) {
                case "storage" :
                    currentMode = 0;
                    break;
                case "query" :
                    currentMode = 1;
                    break;
                case "delete" :
                    currentMode = 2;
                    break;
                case "quit" :
                    currentMode = -2;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUsername() {
        try {
            outputStream.println("请设置username");

            username = inputStream.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void storage() {
        try {
            outputStream.println("请输入数据,输入/q表示停止输入");
            
            String s = inputStream.readLine();
            while (!"/q".equals(s)) {
                long seq = Context.getSeq();
                String m = "数据保存成功。消息序列号为: " + seq;
                storage.store(new Message(username, s, new Date().getTime(), socket.getInetAddress().getHostAddress(), seq));
                outputStream.println(m);
                s = inputStream.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void query() {
        try {
            outputStream.println("全部查询输入all，精确查询输入消息序列号,输入/q表示停止输入");
            String s = inputStream.readLine();
            while (!"/q".equals(s)) {
                switch (s) {
                    case "all" :
                        ArrayList<Message> queryResult = storage.query(username);
                        if (queryResult.size() == 0) {
                            outputStream.println("无数据");
                            break;
                        }
                        String result = queryResult.stream().map(Message::toString).reduce((x,y) -> x + " || " + y).get();
                        outputStream.println(result);
                        break;
                    default:
                        ArrayList<Message> query = storage.query(username);
                        String r;
                        try {
                            int index = query.indexOf(new Message(Long.parseLong(s)));
                            if (index == -1) {
                                r = "数据不存在";
                            } else {
                                r = query.get(query.indexOf(new Message(Long.parseLong(s)))).toString();
                            }
                            outputStream.println(r);
                        } catch (NumberFormatException e) {
                            outputStream.println("序列号必须是数字");
                        }
                }
                s = inputStream.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void delete() {
        try {
            outputStream.println("请输入删除序列号,删除所有输入all，/q退出");
            String s = inputStream.readLine();
            while (!"/q".equals(s)) {
                String m;
                if("all".equals(s)) {
                    int r = storage.delete(new Message(username,null,null,null,-1),true);
                    m = r == 1 ? "数据全部删除成功" : "无数据";
                } else {
                    try {
                        long l = Long.parseLong(s);
                        int r = storage.delete(new Message(username, null, null, null, l),false);
                        m = r == 1 ? "删除成功，删除的消息序列号为：" + s : "此数据不存在";
                    } catch (NumberFormatException e) {
                        m = "序列号必须是数字";
                    }
                }
                outputStream.println(m);
                s = inputStream.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
