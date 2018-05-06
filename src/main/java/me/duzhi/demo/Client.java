package me.duzhi.demo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        BufferedReader inputReader = null;
        BufferedWriter writer = null;
        Socket socket = null;
        // Alt + Shift + z    try...catch等快捷键
        // 从Console读取内容
        try {
            socket = new Socket("127.0.0.1", 8080);
            // 从socket进行写入
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            inputReader = new BufferedReader(new InputStreamReader(System.in));
            String inputContent;
            while (!(inputContent = inputReader.readLine()).equals("bye")) {
                writer.write(inputContent + "\n");
                writer.flush();
//              System.out.println(inputContent);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
