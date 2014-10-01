package com.workable.camel.component;

import com.workable.camel.component.vo.TestMessage;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RabbitMQSpringIT extends CamelSpringTestSupport {

	@EndpointInject(uri = "direct:start")
	private ProducerTemplate template;

	@EndpointInject(uri = "mock:result")
	private MockEndpoint result;

	@Test
	public void testEndpointCreation() throws Exception{

		result.expectedMessageCount(1);
		template.send("direct:start", new Processor() {
			public void process(Exchange exchange) throws Exception {
				TestMessage testMessage = new TestMessage("1", System.currentTimeMillis());
				exchange.getIn().setBody(testMessage);
			}
		});

		assertMockEndpointsSatisfied();
	}


	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("rabbitmq-spring-context.xml");
	}
}
