package com.vicyor.netty.client;

import com.vicyor.netty.client.model.RequestMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NettyClient {
    private Bootstrap clientBootStap;
    private Map<Long, CallbackFuture> reqAndRespMap;
    private Executor executor = Executors.newSingleThreadExecutor();

    public NettyClient() {
        clientBootStap = new Bootstrap();
        reqAndRespMap = new HashMap<Long, CallbackFuture>();
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);
        final NettyClient client = this;
        clientBootStap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ClientReceiverHandler(client));
                    }
                });
    }

    public Channel connect(String host, int port) {
        ChannelFuture future = clientBootStap.connect(
                new InetSocketAddress(host, port)
        );
        return future.channel();
    }

    public Map<Long, CallbackFuture> getReqAndRespMap() {
        return reqAndRespMap;
    }

    public void write(RequestMsg requestMsg, ProcessResponse processTask, String host, int port) {
        Channel channel = connect(host, port);
        channel.write(requestMsg);
        CallbackFuture future = new CallbackFuture(requestMsg);
        processTask.setCallbackFuture(future);
        reqAndRespMap.put(requestMsg.getRequestId(), future);
        executor.execute(processTask);
    }
}