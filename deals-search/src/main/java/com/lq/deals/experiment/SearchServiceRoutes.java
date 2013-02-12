package com.lq.deals.experiment;

import org.apache.camel.builder.RouteBuilder;
import static com.lq.deals.experiment.HtmlFormatterBean.AS_SEARCH_RESULTS_PAGE;
import static com.lq.deals.experiment.HtmlFormatterBean.AS_LINKS;
import static com.lq.deals.experiment.ElasticSearchBean.SEARCH_METHOD;
public class SearchServiceRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jetty://http://0.0.0.0:8080/search")
                .setBody(header("query"))
                .to("direct:search")
                .bean(HtmlFormatterBean.class, AS_SEARCH_RESULTS_PAGE);

        from("direct:search")
                .log("Searching: '${body}'.")
                .beanRef("elasticSearchBean", SEARCH_METHOD)
                .bean(HtmlFormatterBean.class, AS_LINKS)
                .log("Found results: '${body}'.");
    }

}
