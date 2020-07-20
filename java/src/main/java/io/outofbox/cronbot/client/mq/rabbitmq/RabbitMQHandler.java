package io.outofbox.cronbot.client.mq.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.outofbox.cronbot.client.model.job.Job;
import io.outofbox.cronbot.client.mq.IMQCallback;
import io.outofbox.cronbot.client.model.MQConfiguration;
import io.outofbox.cronbot.client.mq.MQHandler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQHandler extends MQHandler {
    private static final String TYPE = "RabbitMQ";
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public RabbitMQHandler(MQConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected String getType() {
        return TYPE;
    }

    private static ConnectionFactory initRabbitMQConnectionFactory(MQConfiguration configuration) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(configuration.getHost());
        connectionFactory.setUsername(configuration.getUsername());
        connectionFactory.setPassword(configuration.getPassword());
        connectionFactory.setVirtualHost(configuration.getVirtualHost());
        connectionFactory.setPort(configuration.getPort());
        return connectionFactory;
    }

    private static Connection newRabbitMQConnection(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    private static Channel createRabbitMQChannel(Connection connection) throws IOException {
        return connection.createChannel();
    }

    private static void declareRabbitMQQueue(String queue, Channel channel) throws IOException {
        channel.queueDeclare(queue, true, false, false, null);
    }

    private static void consumeRabbitMQQueue(String queue, Channel channel, DeliverCallback callback) throws IOException {
        channel.basicConsume(queue, true, callback, consumerTag -> {
        });
    }

    private static Job convertStringToJob(String message) throws IOException {
        Job job =  OBJECT_MAPPER.readValue(message, Job.class);
        return job;
    }

    @Override
    public void receiveJob(IMQCallback callback) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = initRabbitMQConnectionFactory(configuration);
        Connection connection = newRabbitMQConnection(connectionFactory);
        Channel channel = createRabbitMQChannel(connection);
        declareRabbitMQQueue(configuration.getInQueue(), channel);
        consumeRabbitMQQueue(configuration.getInQueue(), channel, (consumerTag, delivery) -> {
            final String message = new String(delivery.getBody(), "UTF-8");
            callback.handle(convertStringToJob(message));
        });
    }
}
