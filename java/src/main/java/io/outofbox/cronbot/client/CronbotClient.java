package io.outofbox.cronbot.client;

import io.outofbox.cronbot.client.exception.ConfigurationException;
import io.outofbox.cronbot.client.model.MQConfiguration;
import io.outofbox.cronbot.client.model.Options;
import io.outofbox.cronbot.client.model.PluginConfiguration;
import io.outofbox.cronbot.client.model.PluginExecutor;
import io.outofbox.cronbot.client.mq.IMQCallback;
import io.outofbox.cronbot.client.mq.MQHandler;
import io.outofbox.cronbot.client.mq.MQHandlerFactory;

import javax.naming.OperationNotSupportedException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeoutException;

/**
 * Cronbot client class
 *
 * @author Ali Helmy
 */
public class CronbotClient {

    private String token;
    private String coreServerUrl;
    private MQConfiguration configuration;

    private CronbotClient() throws ConfigurationException {
        this(new Options(System.getenv("TOKEN"), System.getenv("CORE_URL")));
    }

    private CronbotClient(Options options) throws ConfigurationException {
        this.token = options.getToken();
        this.coreServerUrl = options.getCoreURL();
        getConfig();
    }

    /**
     * Create new cronbot client with default options ( read TOKEN, CORE_URL ) from environment variables
     *
     * @return CronbotClient instance
     */
    public static CronbotClient create() throws ConfigurationException {
        return new CronbotClient();
    }

    /**
     * reate new cronbot client with options ( read token , coreURL ) from options object
     *
     * @param options mandatory token & coreURL
     * @return CronbotClient instance
     */
    public static CronbotClient create(Options options) throws ConfigurationException {
        return new CronbotClient(options);
    }

    /**
     * Return message queueing configuration
     *
     * @return MQConfiguration connection details
     * @throws ConfigurationException if any error occurs
     */
    private MQConfiguration getConfig() throws ConfigurationException {
        final Client client = ClientBuilder.newClient();
        final PluginExecutor pluginExecutor = new PluginExecutor(this.token);

        try {
            final Response response = client.target(URI.create(this.coreServerUrl)).request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(pluginExecutor, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200) {
                PluginConfiguration pluginConfiguration = response.readEntity(PluginConfiguration.class);
                return configuration = pluginConfiguration.getConfiguration();
            } else {
                throw new ConfigurationException();
            }
        } catch (Exception ex) {
            throw new ConfigurationException(ex);
        }
    }

    /**
     * Receive a job from message queuing server by passing callback
     * Example:
     * CronbotClient client = CronbotClient.create();
     * client.receiveJob((job)->{
     *      System.out.println(job);
     * });
     * @param callback
     * @throws IOException Error occur in MQ
     * @throws TimeoutException Error occur in MQ
     */
    public void receiveJob(final IMQCallback callback) throws IOException, TimeoutException, OperationNotSupportedException {
        MQHandler mqHandler = MQHandlerFactory.newHandler(configuration);
        mqHandler.receiveJob(callback);
    }

}
