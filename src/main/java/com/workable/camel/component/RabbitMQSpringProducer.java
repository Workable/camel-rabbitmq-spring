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
		rabbitTemplate.send(configuration.getQueueNames(), messageConverter.toMessage(exchange.getIn().getBody(), populateMessageProperties(exchange)));
	}

	/**
	 * Helper method to add message headers with specific prefix as Message Properties in AMQP
	 * @param exchange
	 * @return
	 */
	protected MessageProperties populateMessageProperties(Exchange exchange){
		MessageProperties messageProperties = new MessageProperties();
		if(configuration.getPropertiesPrefix() != null){
			for(String headerName : exchange.getIn().getHeaders().keySet()){
				if(headerName != null && headerName.startsWith(configuration.getPropertiesPrefix().toLowerCase())){
					messageProperties.setHeader(headerName, exchange.getIn().getHeader(headerName));
				}
			}
		}
		//Default required properties
		messageProperties.setHeader(Exchange.BREADCRUMB_ID, exchange.getIn().getHeader(Exchange.BREADCRUMB_ID));
		return messageProperties;
	}
}
