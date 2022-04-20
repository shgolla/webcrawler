package com.demo.crawler;

/**
 * WebCrawler Interface to support Web Crawling Functionality
 */
public interface WebCrawler {

    /**
     * Crawls all web pages and it's nested links recursively for given max depth
     * @param url
     * @param max_level
     */
    public void crawl(String url, int max_level);
}
