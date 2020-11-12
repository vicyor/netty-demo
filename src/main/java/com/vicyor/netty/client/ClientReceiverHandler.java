package com.vicyor.netty.client;

import com.vicyor.netty.client.model.ResponseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientReceiverHandler extends ChannelInboundHandlerAdapter {
    private final NettyClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseMsg response= (ResponseMsg) msg;
        Long requestId = response.getRequestId();
        CallbackFuture callBack = client.getReqAndRespMap().get(requestId);
        callBack.set(response);
    }

    public ClientReceiverHandler(NettyClient client) {
        this.client=client;
    }
}
