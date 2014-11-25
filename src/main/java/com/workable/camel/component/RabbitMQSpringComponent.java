package com.workable.camel.component;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class RabbitMQSpringComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

        RabbitMQSpringConfiguration configuration = new RabbitMQSpringConfiguration();
        setProperties(configuration, parameters);
        configuration.setQueueNames(remaining);
        RabbitMQSpringEndpoint endpoint = new RabbitMQSpringEndpoint(uri, this, configuration);
        return endpoint;
    }

}
