package com.demo.reader.impl;

import com.demo.exception.WebCrawlerException;
import com.demo.reader.WebPageReader;
import com.demo.util.WebCrawlerUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to read webpage and provide links from it
 * Implements Webpage Reader and uses Jsoup library to read web contents
 */
public class JsoupWebPageReader implements WebPageReader {
    WebCrawlerUtil util = new WebCrawlerUtil();

    /**
     * Reads webpage and gets all links from given webpage
     * Returns list of urls in a given webpage
     * @param url - webpage to read
     * @return List<String>
     */
    public List<String> readPageAndGetLinks (String url) {
        util.checkForNullOrEmptyString(url);
        Connection connect = Jsoup.connect(url);
        List<String> linksFromPage = null;
        try {
            Document document = connect.get();
            if (connect.response().statusCode() == 200) {
                //return Optional.of(document);
                Elements linkElements = document.select("a[href]");
                linksFromPage = linkElements.stream().map(element -> element.attr("href")).collect(Collectors.toList());
            } else {
                throw new WebCrawlerException("Unable to get the page for given URL "+url);
            }
        } catch (IOException e) {
            throw new WebCrawlerException("Unable to get the page for given URL "+url, e);
        }
        return linksFromPage;
    }
}
