package com.workable.camel.component;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQSpringConfiguration {

	private CachingConnectionFactory cachingConnectionFactory;
	private RabbitTemplate producerTemplate;
	private String queueNames;
	private int concurrentConsumers;
	private int maxConcurrentConsumers;
	private Boolean autoStartup;
	private String propertiesPrefix;

	public CachingConnectionFactory getCachingConnectionFactory() {
		return cachingConnectionFactory;
	}

	public void setCachingConnectionFactory(CachingConnectionFactory cachingConnectionFactory) {
		this.cachingConnectionFactory = cachingConnectionFactory;
	}

	public RabbitTemplate getProducerTemplate() {
		return producerTemplate;
	}

	public void setProducerTemplate(RabbitTemplate producerTemplate) {
		this.producerTemplate = producerTemplate;
	}

	public String getQueueNames() {
		return queueNames;
	}

	public void setQueueNames(String queueNames) {
		this.queueNames = queueNames;
	}

	public int getConcurrentConsumers() {
		return concurrentConsumers;
	}

	public void setConcurrentConsumers(int concurrentConsumers) {
		this.concurrentConsumers = concurrentConsumers;
	}

	public Boolean getAutoStartup() {
		return autoStartup;
	}

	public void setAutoStartup(Boolean autoStartup) {
		this.autoStartup = autoStartup;
	}

	public int getMaxConcurrentConsumers() {
		return maxConcurrentConsumers;
	}

	public void setMaxConcurrentConsumers(int maxConcurrentConsumers) {
		this.maxConcurrentConsumers = maxConcurrentConsumers;
	}

	public String getPropertiesPrefix() {
		return propertiesPrefix;
	}

	public void setPropertiesPrefix(String propertiesPrefix) {
		this.propertiesPrefix = propertiesPrefix;
	}
}
