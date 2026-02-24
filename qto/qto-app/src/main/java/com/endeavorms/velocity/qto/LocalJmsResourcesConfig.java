package com.endeavorms.velocity.qto;

import jakarta.jms.Connection;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mysql")
@ConditionalOnProperty(prefix = "app.jms", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LocalJmsResourcesConfig {

    @Bean(name = "queue")
    public Queue queue() {
        return new ActiveMQQueue("qto.FtdiProcessingQueue");
    }

    @Bean(name = "connectionFactory", destroyMethod = "close")
    public QueueConnectionFactory connectionFactory(
            @Value("${qto.jms.broker-url:vm://0}") String brokerUrl) {

        ActiveMQConnectionFactory delegate = new ActiveMQConnectionFactory(brokerUrl);
        return new QueueConnectionFactoryAdapter(delegate);
    }

    static final class QueueConnectionFactoryAdapter implements QueueConnectionFactory, AutoCloseable {

        private final ActiveMQConnectionFactory delegate;

        QueueConnectionFactoryAdapter(ActiveMQConnectionFactory delegate) {
            this.delegate = delegate;
        }

        @Override
        public Connection createConnection() throws JMSException {
            return delegate.createConnection();
        }

        @Override
        public Connection createConnection(String userName, String password) throws JMSException {
            return delegate.createConnection(userName, password);
        }

        @Override
        public QueueConnection createQueueConnection() throws JMSException {
            return (QueueConnection) delegate.createConnection();
        }

        @Override
        public QueueConnection createQueueConnection(String userName, String password) throws JMSException {
            return (QueueConnection) delegate.createConnection(userName, password);
        }

        @Override
        public JMSContext createContext() {
            return delegate.createContext();
        }

        @Override
        public JMSContext createContext(int sessionMode) {
            return delegate.createContext(sessionMode);
        }

        @Override
        public JMSContext createContext(String userName, String password) {
            return delegate.createContext(userName, password);
        }

        @Override
        public JMSContext createContext(String userName, String password, int sessionMode) {

















































































































































































































































            
            return delegate.createContext(userName, password, sessionMode);
        }

        @Override
        public void close() {
            delegate.close();
        }
    }
}
