package io.outofbox.cronbot.client.model.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class Integration {
    private String id;
    private String name;
    private String description;
    @JsonProperty("plugin_definition_values")
    private Map<String, Object> pluginDefValues;
    @JsonProperty("plugin")
    private Plugin plugin;

}
