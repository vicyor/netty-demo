package com.vicyor.netty.client;

import com.vicyor.netty.client.model.RequestMsg;
import com.vicyor.netty.client.model.ResponseMsg;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallbackFuture implements Future<ResponseMsg> {
    private Object lock = new Object();
    private ResponseMsg responseMsg;
    private RequestMsg requestMsg;

    public CallbackFuture(RequestMsg requestMsg) {
        this.requestMsg = requestMsg;
    }

    @Deprecated
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Deprecated
    public boolean isCancelled() {
        return false;
    }

    @Deprecated
    public boolean isDone() {
        return responseMsg != null;
    }

    public void set(ResponseMsg responseMsg) {
        this.responseMsg = responseMsg;
        synchronized (lock) {
            notifyAll();
        }
    }

    public ResponseMsg get() throws InterruptedException, ExecutionException {
        if (!isDone()) {
            synchronized (lock) {
                wait();
            }
        }

        return responseMsg;
    }

    @Deprecated
    public ResponseMsg get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
