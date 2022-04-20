package com.demo.util;

import com.demo.exception.WebCrawlerException;
import com.google.common.net.InternetDomainName;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Webcrawler utility class holding helper methods for web crawling functionality
 */
public class WebCrawlerUtil {

    /**
     * Checks if a given url starts with the given domain name
     * Returns true if url starts with given domain name else returns false
     * @param url - url to be compared
     * @param domainName - domain name to be compared with
     * @return boolean
     */
    public boolean isUrlStartsWithTopDomain(String url, String domainName) {
        checkForNullOrEmptyString(url);
        boolean urlMatchesTopDomain;
        try {
            URI uri = new URI(url);
            urlMatchesTopDomain = domainName.equals(uri.getHost()) ? true : false;
        } catch (URISyntaxException e) {
            throw new WebCrawlerException("Unable to check if url " + url + " starts with Top Domain name " +domainName , e);
        }
        return urlMatchesTopDomain;
    }

    /**
     * Returns the portion of this domain name that is one level beneath the
     * public suffix. For example, for {@code x.adwords.google.co.uk} it returns
     * {@code google.co.uk}, since {@code co.uk} is a public suffix. Similarly, for {@code
     * myblog.blogspot.com} it returns the same domain, {@code myblog.blogspot.com}, since {@code
     * blogspot.com} is a public suffix.
     * @param url - url for which domain name needs to be retrieved
     * @return STring
     */

    public String getTopPrivateDomainName(String url) {
        checkForNullOrEmptyString(url);
        String domainName = null;
        try {
            domainName = new URI(url).getHost();
            domainName = InternetDomainName.from(domainName).topPrivateDomain().toString();
        } catch (URISyntaxException e) {
            throw new WebCrawlerException("Unable to get Domain name for url "+url , e);
        }
        return domainName;
    }

    /**
     * Throws exception if null or empty string is passed
     * @param url
     */
    public void checkForNullOrEmptyString(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("url cannot be null or empty string");
        }
    }

}
