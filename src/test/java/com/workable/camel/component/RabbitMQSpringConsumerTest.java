package com.workable.camel.component;

import com.rabbitmq.client.LongString;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class RabbitMQSpringConsumerTest {

	private RabbitMQSpringConsumer rabbitMQSpringConsumer;

	@Before
	public void init(){

		RabbitMQSpringEndpoint endpoint = mock(RabbitMQSpringEndpoint.class);
		Processor processor = mock(Processor.class);
		RabbitMQSpringConfiguration configuration = mock(RabbitMQSpringConfiguration.class);
		when(configuration.getPropertiesPrefix()).thenReturn("prefix");
		when(configuration.getConcurrentConsumers()).thenReturn(1);
		when(configuration.getMaxConcurrentConsumers()).thenReturn(1);
		rabbitMQSpringConsumer = new RabbitMQSpringConsumer(endpoint, processor, configuration);
	}

	@Test
	public void testPopulateExchangeHeaders(){
		Map<String, Object> headers = new HashMap<>();
		headers.put("prefix-1", populateListOfStrings());
		headers.put("prefix-2", populateListOfLongStrings());
		Map<String, Object> headersAfterDeserialization = rabbitMQSpringConsumer.populateExchangeHeaders(headers);
		List<Object> prefix2 = (List)headersAfterDeserialization.get("prefix-2");
		assertEquals(prefix2.size(), 1);
		assertEquals(prefix2.get(0), "1");
	}

	/**
	 * Helper method to populate list of Strings
	 * @return
	 */
	private List<String> populateListOfStrings(){
		List<String> result = new ArrayList<>();
		result.add("1");
		result.add("2");
		result.add("3");
		return result;
	}

	/**
	 * Helper method to populate list of Strings
	 * @return
	 */
	private List<LongString> populateListOfLongStrings(){
		List<LongString> result = new ArrayList<>();
		LongString mock = mock(LongString.class);
		when(mock.toString()).thenReturn("1");
		result.add(mock);
		return result;
	}
}
