package com.lq.deals.experiment;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpOperationFailedException;


import com.lq.deals.experiment.HtmlFormatterBean;
import com.lq.deals.experiment.HttpErrorHelperBean;

import static com.lq.deals.experiment.HtmlFormatterBean.AS_EXTRACTED_RESULTS_PAGE;
import static com.lq.deals.experiment.HtmlImproverRoutes.HTML_IMPROVER_EP;
public class PageExtractorRoutes extends RouteBuilder {
    public static final String PAGE_EXTRACTOR_EP = "direct:page_extractor";

    @Override
    public void configure() throws Exception {
        from(PAGE_EXTRACTOR_EP)
                .streamCaching()
                .onException(HttpOperationFailedException.class)
                .onWhen(bean(HttpErrorHelperBean.class).isEqualTo(true))
                .log("Fetching URL failed: '${exception.message}', trying with relocation: '${exception.redirectLocation}'.")
                .handled(true)
                .setBody(simple("${exception.redirectLocation}"))
                .to(PAGE_EXTRACTOR_EP)
                .end()
                .setHeader(Exchange.HTTP_URI, body())
                .setBody(constant(null))
                .log("Extracting content from: '${header." + Exchange.HTTP_URI + "}'")
                .pipeline("http:extractor", HTML_IMPROVER_EP)
                .log("Html from: '${body}'")
                .split(xpath("//body//p/text()"), new SplitterAggregationStrategy("(?s).*[A-Za-z0-9].*"))
                .log("Text chunk: '${body}'.")
                .end();

        from("jetty://http://0.0.0.0:8080/page_extractor")
                .setBody(header("extractUrl"))
                .to(PAGE_EXTRACTOR_EP)
                .bean(HtmlFormatterBean.class, AS_EXTRACTED_RESULTS_PAGE);
    }
}

