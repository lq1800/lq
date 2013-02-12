package com.lq.deals.experiment;

import java.util.ArrayList;
import java.util.List;

public class HtmlFormatterBean {

    public static final String AS_EXTRACTED_RESULTS_PAGE = "asExtractedResultsPage";
    public static final String AS_SEARCH_RESULTS_PAGE = "asSearchResultsPage";
    public static final String AS_LINKS = "asLinks";

    public String asExtractedResultsPage(List<String> contents) {
        return asPage(contents, "Extracted contents:");
    }

    public String asSearchResultsPage(List<String> contents) {
        return asPage(contents, "Search results:");
    }

    private String asPage(List<String> contents, String title) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(String.format("<html><body><h1>%s</h1><ul>", title));
        for (String content : contents) {
            stringBuffer.append("<li>").append(content).append("</li>");
        }
        stringBuffer.append("</ul></body></html>");

        return stringBuffer.toString();
    }

    public List<String> asLinks(List<String> urls) {
        List<String> result = new ArrayList<String>();

        for (String url : urls) {
            result.add(String.format("<a href='%1$s'>%1$s</a>", url));
        }

        return result;
    }
}
