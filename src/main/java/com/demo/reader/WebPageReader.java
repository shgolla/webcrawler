package com.demo.reader;

import java.util.List;

/**
 * Interface to read a web page
 */
public interface WebPageReader {

    /**
     * Reads the content of given web url and returns all links from the page
     * @param url
     * @return List<String>
     */
    public List<String> readPageAndGetLinks (String url);
}
