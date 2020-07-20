package io.outofbox.cronbot.client.exception;

public class ConfigurationException extends Throwable {
    private static final String MESSAGE = "Failed to read configuration from core server";

    public ConfigurationException(){
        super(MESSAGE);
    }

    public ConfigurationException(Throwable th){
        super(MESSAGE, th);
    }
}
