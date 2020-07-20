package io.outofbox.cronbot.client;

import io.outofbox.cronbot.client.exception.ConfigurationException;
import io.outofbox.cronbot.client.model.MQConfiguration;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ConfigurationException, IOException, TimeoutException, OperationNotSupportedException {
        CronbotClient client = CronbotClient.create();
        client.receiveJob((job)->{
            System.out.println(job);
        });
    }
}
