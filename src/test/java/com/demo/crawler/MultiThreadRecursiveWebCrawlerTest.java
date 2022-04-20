package com.demo.crawler;

import com.demo.crawler.impl.MultiThreadRecursiveWebCrawler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MultiThreadRecursiveWebCrawlerTest {

    String startUrl = "https://monzo.com";
    MultiThreadRecursiveWebCrawler webCrawler = new MultiThreadRecursiveWebCrawler(startUrl, 1);

    @Test
    void ShouldCrawlOnceForGivenPageANdReturnLinksInIt() {
        MultiThreadRecursiveWebCrawler webCrawler = new MultiThreadRecursiveWebCrawler(startUrl, 1);
        Map<String, List<String>> visitedLinksAndTheirUrls = webCrawler.call();
        assertTrue(visitedLinksAndTheirUrls.size() == 1);
        assertTrue(visitedLinksAndTheirUrls.keySet().toArray()[0].equals(startUrl));
        assertTrue(visitedLinksAndTheirUrls.values().size() > 0);
    }

    @Test
    void ShouldCrawlTenLevelsForGivenPageANdReturnLinksInEachPage() {
        MultiThreadRecursiveWebCrawler webCrawler = new MultiThreadRecursiveWebCrawler(startUrl, 10);
        Map<String, List<String>> visitedLinksAndTheirUrls = webCrawler.call();
        assertTrue(visitedLinksAndTheirUrls.size() == 10);
        for(Map.Entry<String, List<String>> entry  : visitedLinksAndTheirUrls.entrySet()) {
            assertTrue(entry.getKey().startsWith(startUrl));
            assertTrue(entry.getValue().size() > 0);
        }

    }

    @Test
    void ShouldNotAllowToVisit_WhenVisitedUrl_isSentToVisitAgain() {
        String url = "https://monzo.com/i/business";
        Map<String, List<String>> visitedLinksAndTheirUrls = new HashMap<>();
        visitedLinksAndTheirUrls.put(url, null);
        assertFalse(webCrawler.canVisitUrl(url, visitedLinksAndTheirUrls));
    }

    @Test
    void ShouldAllowToVisit_WhenNonVisitedUrl_isSentToVisit() {
        Map<String, List<String>> visitedLinksAndTheirUrls = new HashMap<>();
        assertTrue(webCrawler.canVisitUrl("https://monzo.com", visitedLinksAndTheirUrls));
    }

    @Test
    void ShouldNotAllowToVisit_WhenHashOrSlash_isSentToVisit() {
        String url = "https://monzo.com/i/business";
        Map<String, List<String>> visitedLinksAndTheirUrls = new HashMap<>();
        visitedLinksAndTheirUrls.put(url, null);
        assertFalse(webCrawler.canVisitUrl("#", visitedLinksAndTheirUrls));
        assertFalse(webCrawler.canVisitUrl("/", visitedLinksAndTheirUrls));
    }

    @Test
    void ShouldNotAllowToVisit_WhenUrlDoesntStartWithStartUrlDomainName() {
        Map<String, List<String>> visitedLinksAndTheirUrls = new HashMap<>();
        assertFalse(webCrawler.canVisitUrl("https://community.monzo.com/i/business", visitedLinksAndTheirUrls));
        assertFalse(webCrawler.canVisitUrl("https://facebook.com/i/business", visitedLinksAndTheirUrls));
    }

    @Test
    void ShouldAllowToVisit_WhenUrlStartsWithStartUrlDomainName() {
        Map<String, List<String>> visitedLinksAndTheirUrls = new HashMap<>();
        assertTrue(webCrawler.canVisitUrl("https://monzo.com/i/business", visitedLinksAndTheirUrls));
    }

}