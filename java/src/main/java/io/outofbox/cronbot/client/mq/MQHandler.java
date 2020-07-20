package io.outofbox.cronbot.client.mq;

import io.outofbox.cronbot.client.model.MQConfiguration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class MQHandler {
    protected MQConfiguration configuration;
    public MQHandler(MQConfiguration configuration){
        this.configuration =configuration;
    }
    public abstract void receiveJob(IMQCallback callback) throws IOException, TimeoutException;

    protected abstract String getType();
}
