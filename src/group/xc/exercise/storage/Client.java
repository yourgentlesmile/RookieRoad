package group.xc.exercise.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 客户端
 *
 * @Date 2020/4/17 13:35.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream outputStream = new PrintStream(socket.getOutputStream());
        String read = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!"pqpq".equals(input) && !socket.isClosed()) {
            String readLine = bufferedReader.readLine();
            System.out.println(readLine);
            if("goodbye".equals(readLine)) break;
            input = reader.readLine();
            outputStream.println(input);

        }

    }
}

