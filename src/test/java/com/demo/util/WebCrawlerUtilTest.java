package com.demo.util;

import com.demo.exception.WebCrawlerException;
import com.demo.util.WebCrawlerUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.Assert.*;

public class WebCrawlerUtilTest{

    WebCrawlerUtil webCrawlerUtil = new WebCrawlerUtil();

    @Test
    public void assertFalse_WhenUrlDoesntStartWithGivenDomainName() {
        assertFalse(webCrawlerUtil.isUrlStartsWithTopDomain("https://community.monzo.com", "monzo.com"));

    }

    @Test
    public void assertTrue_WhenUrlStartWithGivenDomainName() {
        assertTrue(webCrawlerUtil.isUrlStartsWithTopDomain("https://monzo.com/i/business/eligibility/", "monzo.com"));

    }




    @Test
    public void shouldThrowException_WhenUrlisMalformed() {
        Exception exception = assertThrows(WebCrawlerException.class, () -> {
            webCrawlerUtil.isUrlStartsWithTopDomain("https://monzo.com/i/business/eligibility/ ", "monzo.com");
        });
    }

    @Test
    public void assertTrue_WhenExpectedDomainNameIsReturned() {
        String topPrivateDomainName = webCrawlerUtil.getTopPrivateDomainName("https://monzo.com/test");
        assertEquals("monzo.com", topPrivateDomainName);
    }

    @Test
    void shouldThrowExceptionWhenEmptyOrNullStringIsPassed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            webCrawlerUtil.checkForNullOrEmptyString("  ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            webCrawlerUtil.checkForNullOrEmptyString(null);
        });
    }
}