package me.duzhi.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Demo1 {
    private static final int PORT = 8888;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void main(String[] args) {
        Demo1 nettyTelnetServer = new Demo1();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            nettyTelnetServer.close();
        }
    }

    public void open() throws InterruptedException {

        serverBootstrap = new ServerBootstrap();
        // 指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)  // 指定是一个NIO连接通道
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        // 绑定对应的端口号,并启动开始监听端口上的连接
        Channel ch = serverBootstrap.bind().sync().channel();

        // 等待关闭,同步端口
        ch.closeFuture().sync();
    }
    public void close(){

    }



    public static class NettyTelnetInitializer extends ChannelInitializer<SocketChannel> {

        private static final StringDecoder DECODER = new StringDecoder();
        private static final StringEncoder ENCODER = new StringEncoder();

        @Override
        public void initChannel(SocketChannel channel) throws Exception {

            ChannelPipeline pipeline = channel.pipeline();

            // Add the text line codec combination first,
            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

            // 添加编码和解码的类
            pipeline.addLast(DECODER);
            pipeline.addLast(ENCODER);

            // 添加处理业务的类
            pipeline.addLast(new NettyTelnetHandler());

        }

        private class NettyTelnetHandler implements ChannelHandler {

            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                System.out.println("channelActive");
                // Send greeting for a new connection.
                ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
                ctx.write("It is " + new Date() + " now.\r\n");
                ctx.flush();
            }

            public void beforeAdd(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("beforeAdd");
            }

            public void afterAdd(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("afterAdd");
            }

            public void beforeRemove(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("beforeRemove");
            }

            public void afterRemove(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("afterRemove");
            }


            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                cause.printStackTrace();
                ctx.close();
            }

            public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                System.out.println("userEventTriggered");
            }


            protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {

                String response;
                boolean close = false;
                if (request.isEmpty()) {
                    response = "Please type something.\r\n";
                } else if ("bye".equals(request.toLowerCase())) {
                    response = "Have a good day!\r\n";
                    close = true;
                } else {
                    response = "Did you say '" + request + "'?\r\n";
                }

                ChannelFuture future = ctx.write(response);
                ctx.flush();
                if (close) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            }


            public EventExecutor next() {
                return null;
            }

            public void shutdown() {

            }

            public boolean isShutdown() {
                return false;
            }

            public boolean isTerminated() {
                return false;
            }

            public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
                return false;
            }
        }
    }


}
