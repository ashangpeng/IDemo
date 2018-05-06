package me.duzhi.demo.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Socket2 {
    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket(89);
        //创建线程池制作伪线程状态
        ExecutorService service = Executors.newFixedThreadPool(200);
        while (true){
            Socket socket = socketServer.accept();
            service.execute(new Execute(socket));
        }
    }

    public static class Execute implements Runnable {
        private Socket socket;
        public Execute(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
