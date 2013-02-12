package com.lq.deals.experiment;

import org.apache.camel.builder.RouteBuilder;
import static com.lq.deals.experiment.ElasticSearchBean.ID_HEADER;
import static com.lq.deals.experiment.ElasticSearchBean.INDEX_METHOD;
import static com.lq.deals.experiment.PageExtractorRoutes.PAGE_EXTRACTOR_EP;
public class RssExtractorRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rss:http://feeds.bbc.co.uk/news/rss.xml?splitEntries=false")
                .marshal().rss()
                .marshal().string()
                .split(xpath("//item/link/text()"))
                .setHeader(ID_HEADER, body())
                .log("Link: '${body}'")
                .to(PAGE_EXTRACTOR_EP)
                .beanRef("elasticSearchBean", INDEX_METHOD);
    }

}
