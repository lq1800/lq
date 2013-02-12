package com.lq.deals.experiment;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.w3c.dom.Node;

public class SplitterAggregationStrategy implements AggregationStrategy {
	private final String fFilter;

    public SplitterAggregationStrategy(String filter) {
        fFilter = filter;
    }

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List<String> result = null == oldExchange ? result = new ArrayList<String>() : oldExchange.getIn().getBody(List.class);

        Node node = newExchange.getIn().getBody(Node.class);
        String content = node.getNodeValue().trim();
        if (content.matches(fFilter)) {
            result.add(content.trim());
        }

        newExchange.getIn().setBody(result);
        return newExchange;
	}

}
