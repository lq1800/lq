package com.lq.deals.experiment;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

public class HTMLCleanerBean {

    public Document cleanHtml(String html) {
        CleanerProperties properties = new CleanerProperties();
        properties.setNamespacesAware(false);
        HtmlCleaner cleaner = new HtmlCleaner(properties);
        TagNode node = cleaner.clean(html);
        try {
            return new DomSerializer(properties).createDOM(node);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
