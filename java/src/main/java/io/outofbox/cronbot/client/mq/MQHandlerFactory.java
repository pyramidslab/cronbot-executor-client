package io.outofbox.cronbot.client.mq;

import io.outofbox.cronbot.client.model.MQConfiguration;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;

public class MQHandlerFactory {
    private static final String RABBIT_MQ = "RabbitMQ";
    private static final String KAFKA_MQ = "KafkaMQ";
    private static final String CLASS_PATH = "%s.%s.%sHandler";

    public static MQHandler newHandler(MQConfiguration configuration) throws OperationNotSupportedException {
        try {
            String classPath = String.format(CLASS_PATH, MQHandlerFactory.class.getPackage().getName(), configuration.getType().toLowerCase(), configuration.getType());
            Class cls = Class.forName(classPath);
            return (MQHandler) cls.getDeclaredConstructor(MQConfiguration.class).newInstance(configuration);
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        }
        throw new OperationNotSupportedException(String.format("%s is not supported yet", configuration.getType()));
    }
}
