package io.outofbox.cronbot.client.model.job;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum JobStatus {
    NEW("new"),
    STARTED("started"),
    RUNNING("running"),
    STOPPED("stopped"),
    FINISHED("finished"),
    INTERRUPTED("interrupted"),
    ERROR("error");

    private String name;

}
