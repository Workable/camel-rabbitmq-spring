package com.workable.camel.component;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.rabbitmq.client.LongString;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.direct.DirectConsumer;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RabbitMQSpringConsumer extends DirectConsumer implements MessageListener {

	private final RabbitMQSpringConfiguration configuration;
	private final SimpleMessageListenerContainer simpleMessageListenerContainer;

	public RabbitMQSpringConsumer(RabbitMQSpringEndpoint endpoint, Processor processor, RabbitMQSpringConfiguration configuration) {
		super(endpoint, processor);
		this.configuration = configuration;
		this.simpleMessageListenerContainer = new SimpleMessageListenerContainer(configuration.getCachingConnectionFactory());
		simpleMessageListenerContainer.setConcurrentConsumers(configuration.getConcurrentConsumers());
		simpleMessageListenerContainer.setMaxConcurrentConsumers(configuration.getMaxConcurrentConsumers());
		simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
		simpleMessageListenerContainer.setQueueNames(configuration.getQueueNames());
		simpleMessageListenerContainer.setMessageListener(this);
	}

	@Override
	public void doStart() throws Exception {
		//Just start the simpleMessageListenerContainer
		simpleMessageListenerContainer.start();
	}

	@Override
	public void doStop() throws Exception {
		simpleMessageListenerContainer.stop();
	}

	@Override
	public void onMessage(Message message) {
		Exchange exchange = getEndpoint().createExchange();
		try {
			Map<String, Object> messageHeaders = message.getMessageProperties().getHeaders();
			exchange.getIn().setHeaders(populateExchangeHeaders(messageHeaders));
			exchange.getIn().setBody(message.getBody());
			getProcessor().process(exchange);
		} catch (Exception e) {
			exchange.setException(e);
		}
		if (exchange.isFailed()) {
			if (exchange.getException() != null) {
				getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
			}
		}
	}

	/**
	 * Helper method to bootstrap Camel Exchange with message headers from AMQP Message
	 * @param messageHeaders
	 * @return
	 */
	protected Map<String, Object> populateExchangeHeaders(Map<String, Object> messageHeaders){
		Map<String, Object> headers = new HashMap<>();
		if(configuration.getPropertiesPrefix() != null){
			for(String headerName : messageHeaders.keySet()){
				if(headerName != null && headerName.startsWith(configuration.getPropertiesPrefix().toLowerCase())){
					if(messageHeaders.get(headerName) instanceof List){
						Function<Object, Object> stringifyLongStringObjects = new Function<Object, Object>() {
							@Override
							public Object apply(Object input) {
								return (input instanceof LongString)? input.toString() : input;
							}
						};
						List<Object> list = (List)messageHeaders.get(headerName);
						List<Object> result = Lists.transform(list, stringifyLongStringObjects);
						headers.put(headerName, result);
					}else {
						headers.put(headerName, messageHeaders.get(headerName));
					}
				}
			}
		}
		// Required message properties
		headers.put(Exchange.BREADCRUMB_ID, messageHeaders.get(Exchange.BREADCRUMB_ID));
		return headers;
	}

	/**
	 * Transformation of LongString to string
	 * @param messageHeaderObject
	 * @return
	 */
	private Object transform(Object messageHeaderObject){
		Object messageHeader = null;
		if(messageHeaderObject instanceof LongString){
			messageHeader = messageHeaderObject.toString();
		} else {
			messageHeader = messageHeaderObject;
		}
		return messageHeader;
	}
}
