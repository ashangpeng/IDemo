package me.duzhi.demo.socket;

import org.apache.log4j.net.SocketServer;

import java.io.*;
import java.net.*;

public class BlockingSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket(89);

        while (true){
            Socket socket = socketServer.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        }
    }
}
