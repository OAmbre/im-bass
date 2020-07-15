package com.skr.im.access.boot;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author : mengqingwen 
 * create at:  2020-07-12
 * @description:
 * */
public class IMChanelInitializer extends ChannelInitializer<SocketChannel> {

    private IMTextAccessHandler textAccessHandler = new IMTextAccessHandler();
    private AuthHandler authHandler = new AuthHandler();

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(textAccessHandler);
        pipeline.addLast(authHandler);
    }
}