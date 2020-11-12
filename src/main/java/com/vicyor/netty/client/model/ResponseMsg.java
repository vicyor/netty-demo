package com.vicyor.netty.client.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class ResponseMsg implements Serializable {
    // 请求ID
    private Long requestId;
    // 响应内容
    private Object respContent;
}
