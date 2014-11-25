package com.workable.camel.component;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;

public class RabbitMQSpringEndpoint extends DirectEndpoint {

    private RabbitMQSpringConfiguration configuration;

    public RabbitMQSpringEndpoint(String endpointUri, Component component, RabbitMQSpringConfiguration configuration) {
        super(endpointUri, component);
        this.configuration = configuration;
    }

    @Override
    public Producer createProducer() throws Exception {
        RabbitMQSpringProducer rabbitMQSpringProducer = new RabbitMQSpringProducer(this, configuration);
        return rabbitMQSpringProducer;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        RabbitMQSpringConsumer consumer = new RabbitMQSpringConsumer(this, processor, configuration);
        return consumer;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
