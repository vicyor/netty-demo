package com.vicyor.netty.client.model;

import lombok.Data;

@Data
public class RequestMsg {
    // 请求ID
    private Long  requestId;
    // 请求内容
    private Object content;
}
