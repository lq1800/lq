package com.lq.deals.experiment;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {
    public static void main(String[] args) throws Exception {
        final DefaultCamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new PageExtractorRoutes());
        camelContext.addRoutes(new RssExtractorRoutes());
        camelContext.addRoutes(new HtmlImproverRoutes());
        camelContext.start();

        ProducerTemplate template = camelContext.createProducerTemplate();
        String result = template.requestBody("direct:page_extractor", "http://www.dzone.com", String.class);

        System.out.printf("Extracted: '%s'.n", result);

        Thread.sleep(10000);
        camelContext.stop();    }
}
