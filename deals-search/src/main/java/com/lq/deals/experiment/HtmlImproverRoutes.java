package com.lq.deals.experiment;

import org.apache.camel.CamelException;
import org.apache.camel.builder.RouteBuilder;

public class HtmlImproverRoutes extends RouteBuilder {
    public static final String HTML_IMPROVER_EP = "direct:html_improver";

	@Override
	public void configure() throws Exception {
		from(HTML_IMPROVER_EP)
        .onException(CamelException.class)
            .continued(true)
            .log("Tidying failed, trying with Cleaning.")
            .bean(HTMLCleanerBean.class)
        .end()
        .unmarshal().tidyMarkup();

	}

}
