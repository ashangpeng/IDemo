package me.duzhi.demo;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

public class TestNio1 {
    public static final int PORT = 8080;
    public static final Logger logger = Logger.getLogger(TestNio1.class);

    public static void main(String[] args) throws IOException {
        new Thread(new TestServer(8080)).run();
    }

    static class TestServer implements Runnable {
        Selector selector;
        ServerSocketChannel channel;
        int port;

        public TestServer(int port) throws IOException {
            this.port = port;
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.socket().bind(new InetSocketAddress(port), 1024);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
        }

        public void run() {
            while (true) {
                try {
                    selector.select(10000);
                    Set<SelectionKey> set = selector.selectedKeys();
                    Iterator<SelectionKey> seti = set.iterator();
                    while (seti.hasNext()) {
                        process(seti.next());
                        seti.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void process(SelectionKey next) throws IOException {
            if (next.isAcceptable()) {
                ServerSocketChannel channel = (ServerSocketChannel) next.channel();

//                channel.register(next.selector(), SelectionKey.OP_READ);
                channel.register(selector, SelectionKey.OP_READ);//注册读事件
                //sayHello(channel);//对连接进行处理
            }
            if (next.isReadable()) {
                SocketChannel channel = (SocketChannel) next.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                logger.debug("test:read:"+getString(buffer));
                channel.register(next.selector(), SelectionKey.OP_WRITE);

            }
            if (next.isWritable()) {
                SocketChannel channel = (SocketChannel) next.channel();
                channel.write(ByteBuffer.wrap("OK".getBytes()));
            }
        }
    }

    public static String getString(ByteBuffer buffer) {

        Charset charset = null;

        CharsetDecoder decoder = null;

        CharBuffer charBuffer = null;

        try {

            charset = Charset.forName("UTF-8");

            decoder = charset.newDecoder();

            //用这个的话，只能输出来一次结果，第二次显示为空

// charBuffer = decoder.decode(buffer);

            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());

            return charBuffer.toString();

        } catch (Exception ex) {

            ex.printStackTrace();

            return "error";

        }

    }
}
