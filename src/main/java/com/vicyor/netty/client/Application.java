package com.vicyor.netty.client;

import com.vicyor.netty.client.model.RequestMsg;
import com.vicyor.netty.client.model.ResponseMsg;

public class Application {
    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.write(new RequestMsg(10085L, "hello,thankyou,thankyou very muck"), new ProcessResponse() {
            @Override
            protected void process(ResponseMsg response) {
                System.err.println(response.getRespContent());
            }
        }, "127.0.0.1", 8080);
        System.in.read();
    }
}
