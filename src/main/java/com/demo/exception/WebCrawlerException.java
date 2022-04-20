package com.demo.exception;

/**
 * Run Time Exception to wrap any exceptions during WebCrawling
 */
public class WebCrawlerException extends RuntimeException{

    public WebCrawlerException() {
        super();
    }

    public WebCrawlerException(String msg) {
        super(msg);
    }


    public WebCrawlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
