package com.zh.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerApp {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup(2, new ThreadFactory() {

            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "BOSS-" + count.getAndIncrement());
            }
        });

        NioEventLoopGroup work = new NioEventLoopGroup(16, new ThreadFactory() {

            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "WORK-" + count.getAndIncrement());
            }
        });


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class).group(boss, work);
        serverBootstrap.childHandler(new ChannelHandler() {
            @Override
            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

                System.out.println("=========1");
            }

            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                System.out.println("=========2");
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                System.out.println("=========3");
            }
        });
        ChannelFuture bind = serverBootstrap.bind(9468);
        ChannelFuture sync = bind.sync();
        sync.channel().closeFuture().sync();
    }
}
