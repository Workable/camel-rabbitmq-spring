package com.workable.camel.component;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitMQSpringProducer extends DefaultProducer {

	private RabbitMQSpringConfiguration configuration;

	public RabbitMQSpringProducer(Endpoint endpoint, RabbitMQSpringConfiguration configuration) {
		super(endpoint);
		this.configuration = configuration;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		RabbitTemplate rabbitTemplate = configuration.getProducerTemplate();
		MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader(Exchange.BREADCRUMB_ID, exchange.getIn().getHeader(Exchange.BREADCRUMB_ID));
		rabbitTemplate.send(configuration.getQueueNames(), messageConverter.toMessage(exchange.getIn().getBody(), messageProperties));
	}
}
