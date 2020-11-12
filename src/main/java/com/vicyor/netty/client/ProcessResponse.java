package com.vicyor.netty.client;

import com.vicyor.netty.client.model.ResponseMsg;

import java.util.concurrent.ExecutionException;

public abstract class ProcessResponse implements Runnable {
    private final CallbackFuture callbackFuture;

    public ProcessResponse(CallbackFuture callbackFuture) {
        this.callbackFuture = callbackFuture;
    }
    public  void run(){
        // 阻塞式get
        try {
            ResponseMsg response = callbackFuture.get();
            process(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected abstract void process(ResponseMsg response);
}
