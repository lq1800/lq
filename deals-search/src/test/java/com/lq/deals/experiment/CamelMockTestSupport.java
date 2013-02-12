package com.lq.deals.experiment;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.TestSupport;

public class CamelMockTestSupport extends TestSupport {
	private MockEndpoint fMockEndpoint;

    public MockEndpoint getMockEndpoint() {
        if (null == fMockEndpoint) {
            fMockEndpoint = new MockEndpoint("mock:_mock_");
        }
        return fMockEndpoint;
    }

    protected void sendBodyToMock(String endpoint, final Object body) throws Exception {
        final DefaultCamelContext camelContext = new DefaultCamelContext();
        camelContext.start();

        ProducerTemplate template = camelContext.createProducerTemplate();
        Exchange exchange = template.request(endpoint, new Processor() {
			public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody(body);
            }
        });
        exchange.getIn().copyFrom(exchange.getOut());
        template.send(getMockEndpoint(), exchange);
    }
}
