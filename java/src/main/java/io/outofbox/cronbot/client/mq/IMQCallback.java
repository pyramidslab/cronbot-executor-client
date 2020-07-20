package io.outofbox.cronbot.client.mq;

import io.outofbox.cronbot.client.model.job.Job;

@FunctionalInterface
public interface IMQCallback {
    void handle(Job job);
}
