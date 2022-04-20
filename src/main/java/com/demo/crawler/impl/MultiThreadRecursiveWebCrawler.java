package com.demo.crawler.impl;


import com.demo.crawler.WebCrawler;
import com.demo.reader.impl.JsoupWebPageReader;
import com.demo.reader.WebPageReader;
import com.demo.util.WebCrawlerUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Webcrawler program which can crawl and visit all the pages within a given web url
 * for a given nested level recursively
 * <p>
 * This is multi threading compatible, to enable parallel web crawling for any number of web pages
 */
public class MultiThreadRecursiveWebCrawler implements WebCrawler, Callable {
    private int max_level = 1;
    private int level = 1;
    private final String startUrl;
    private final Map<String, List<String>> visitedLinksAndTheirUrls;
    private final String topPrivateDomainName;
    private final WebCrawlerUtil webCrawlerUtil = new WebCrawlerUtil();
    private final WebPageReader webPageReader = new JsoupWebPageReader();

    public MultiThreadRecursiveWebCrawler(String url, int max_level) {
        webCrawlerUtil.checkForNullOrEmptyString(url);
        startUrl = url;
        this.max_level = max_level;
        topPrivateDomainName = webCrawlerUtil.getTopPrivateDomainName(startUrl);
        visitedLinksAndTheirUrls = new HashMap();
    }

    /**
     * Main method invoked when thread is executed to start web crawling
     * @return Map of visited pages and list of links in each visited page
     */
    @Override
    public Map<String, List<String>> call() {
        crawl(startUrl, max_level);
        return visitedLinksAndTheirUrls;
    }

    /**
     * Crawl method which can read web page and visit all urls recursively for given max depth
     * @param url
     * @param max_level
     */
    @Override
    public void crawl(String url, int max_level) {
        if (level <= max_level) { // limit recursiveness to max depth
            List<String> linksFromPage = webPageReader.readPageAndGetLinks(url);
            visitedLinksAndTheirUrls.put(url, linksFromPage);
            for (String nextUrl : linksFromPage) {
                nextUrl = nextUrl.trim();
                //construct full url for relative paths
                nextUrl = nextUrl.startsWith("/") && nextUrl.length() > 1 ? "https://" + topPrivateDomainName + nextUrl : nextUrl;
                if (canVisitUrl(nextUrl, visitedLinksAndTheirUrls)) {
                    level++;
                    crawl(nextUrl, max_level);
                }
            }
        }

    }

    /**
     * Checks if a url can be ignored from visiting while web crawling
     * returns false for same page, all ready visited pages and
     * external domains which doesn't start with domain of given start url
     * returns true if doesn't match with above criteria
     * @param nextUrl
     * @param visitedLinksAndTheirUrls
     * @return boolean
     */
    public boolean canVisitUrl(String nextUrl, Map<String, List<String>> visitedLinksAndTheirUrls) {
        return !nextUrl.equals("#") && !nextUrl.equals("/") && notVisited(nextUrl, visitedLinksAndTheirUrls) && webCrawlerUtil.isUrlStartsWithTopDomain(nextUrl, topPrivateDomainName);
    }

    /**
     * Checks if url is already visited or not
     * Returns true if url is not already visited else returns false
     * @param url
     * @param visitedLinksAndTheirUrls
     * @return boolean
     */
    private boolean notVisited(String url, Map<String, List<String>> visitedLinksAndTheirUrls ) {
        return visitedLinksAndTheirUrls.keySet().stream().noneMatch(visitedLink -> visitedLink.equals(url));
    }

    /**
     * Returns VisitedLinksAndTheirUrls that have been crawled
     * @return Map<String, List<String>>
     */
    public Map<String, List<String>> getVisitedLinksAndTheirUrls() {
        return visitedLinksAndTheirUrls;
    }

}
