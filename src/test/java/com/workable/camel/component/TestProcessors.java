package com.workable.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestProcessors {

    private ProducerTemplate producerTemplate;

    private Logger logger = LoggerFactory.getLogger(TestProcessors.class);

    public void sendToHttpEndpoint(Exchange exchange) throws Exception {
        Object body = producerTemplate.requestBody("http://localhost:9099/test", exchange.getIn().getBody());
        logger.info("Received response: " + body);
    }

    public void respondToHttpCall(Exchange exchange) throws Exception {
        logger.info("Received request: ");
    }

    public void setProducerTemplate(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }
}
