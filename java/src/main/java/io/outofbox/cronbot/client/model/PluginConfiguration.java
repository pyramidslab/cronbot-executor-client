package io.outofbox.cronbot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PluginConfiguration {
    @JsonProperty("plugin_id")
    private String pluginID;
    private String token;
    private MQConfiguration configuration;
}
