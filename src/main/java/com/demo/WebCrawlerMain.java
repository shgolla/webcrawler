package com.demo;

import com.demo.crawler.impl.MultiThreadRecursiveWebCrawler;
import com.demo.exception.WebCrawlerException;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Main class which instantiates multiple webcrawler threads
 * and crawls the pages in parallel to get links contained in each webpage
 * starting from a start url and goes to a maximum nested level that's configured
 */
public class WebCrawlerMain {

    public static Logger logger = LoggerFactory.getLogger(WebCrawlerMain.class);

    /**
     * Uses Executor service to submit multiple webcrawler tasks in parallel and gets the webcrawling results
     * The results printed will show pages visited and links contained in each page for all submitted webcrawler Threads
     * @param args
     */
    public static void main(String[] args) {
        List<String> urls = Arrays.asList("https://monzo.com/", "https://revolut.com");
        ExecutorService executorService = Executors.newFixedThreadPool(urls.size());
        List<Future<Map<String, List<Element>>>> webCrawlerResults = new ArrayList<>();
        try {
            for (String url : urls) {
                webCrawlerResults.add(executorService.submit(new MultiThreadRecursiveWebCrawler(url, 10)));
            }
            int webcrawlerCount = 1;
            for (Future<Map<String, List<Element>>> webCrawlerResult : webCrawlerResults) {
                try {
                    Map<String, List<Element>> visitedLinksAndtheirUrls = webCrawlerResult.get();
                    System.out.println(" ################################ Printing Webcrawler Results ########################");
                    System.out.println("No.of pages visited by webcrawler "+ webcrawlerCount +" : "+ visitedLinksAndtheirUrls.size());
                    System.out.println("Pages visited by webcrawler "+ webcrawlerCount +" are : "+  visitedLinksAndtheirUrls.keySet());
                    System.out.println("Showing all visited Pages and Urls in each page for webcrawler "+ webcrawlerCount);
                    System.out.println(visitedLinksAndtheirUrls);
                    webcrawlerCount++;
                } catch (InterruptedException e) {
                    logger.error("Interrupted Exception while calling webcrawler threads", e);
                } catch (ExecutionException e) {
                    logger.error("ExecutionException while completing Webcrawler Execution", e);
                }

            }
        } catch (WebCrawlerException e) {
            logger.error("WebCrawlerException while completing Webcrawler Execution", e);
        } catch (Exception e) {
            logger.error("Unable to complete Webcrawler Execution due to general error", e);
        } finally {
            executorService.shutdown();
        }



    }
}
