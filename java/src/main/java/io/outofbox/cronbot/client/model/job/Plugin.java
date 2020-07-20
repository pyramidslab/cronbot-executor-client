package io.outofbox.cronbot.client.model.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Plugin {
    private String name;
    private String description;
    @JsonProperty("integration_def")
    private IntegrationDefinition integrationDefinition;
}
