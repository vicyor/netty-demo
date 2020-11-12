package com.vicyor.netty.client;

import com.vicyor.netty.client.model.ResponseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ClientReceiverHandler extends ChannelInboundHandlerAdapter {
    private final NettyClient client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf= (ByteBuf) msg;
        int readableBytes = byteBuf.readableBytes();
        CharSequence charSequence = byteBuf.readCharSequence(readableBytes, Charset.defaultCharset());
        String string = charSequence.subSequence(0, charSequence.length()).toString();
        ResponseMsg response= new ResponseMsg(10085L, string) ;
        Long requestId = response.getRequestId();
        CallbackFuture callBack = client.getReqAndRespMap().get(requestId);
        callBack.set(response);
    }

    public ClientReceiverHandler(NettyClient client) {
        this.client=client;
    }
}
