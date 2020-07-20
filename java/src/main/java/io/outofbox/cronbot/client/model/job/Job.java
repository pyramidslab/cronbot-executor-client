package io.outofbox.cronbot.client.model.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Job {
    private String id;
    private String name;
    private String description;
    private JobStatus status = JobStatus.NEW;
    @JsonProperty("integration")
    private Integration integration;
    @JsonProperty("integration_properties")
    private Map<String, Object> integrationProperties;
    private JobSchedule schedule;
}
