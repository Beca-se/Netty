package com.zh.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerApp {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(2, new ThreadFactory() {

            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "BOSS-" + count.getAndIncrement());
            }
        });

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class).group(boss,boss).childHandler().
    }
}
