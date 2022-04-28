# WebCrawler
A Web crawler, sometimes called a spider or spiderbot and often shortened to crawler, is an Internet bot that systematically browses the World Wide Web and that is typically operated by search engines for the purpose of Web indexing
This is a very simple pet project to simulate part of webcrawler functionality. 

WebCrawler class is responsible to take a starting URL, read its contents and visit all links contained with in each page for a configurable nested level of maximum depth
The program returns visited pages and links contained in each page.

## Requirements that this project achieves
* Given a starting URL, the crawler should visit each URL it finds on the same domain. It should print each URL visited, and a list of links found on that page.
* WebCrawler should not follow sites external to the Top Domain. So when you start with *https://monzo.com/*, it would crawl all pages on the monzo.com website, but not follow external links, for example to facebook.com or community.monzo.com.


## Features
* Java 8 and Gradle based project
* Concurrent Ability - Multi Thread Enabled to spin up and crawl multiple webpages simultaneously
* Extensible for adding more filtering rules to blacklist pages that should not be visited
* Design Thinking - Follows SOLID Principles
* Coded to Interfaces
* Completely unit tested using Junit 5


## Webcrawler class daigram
![WebCrawler Class diagram](/images/webcrawler_classdaigram.png)

## Webcrawler System context diagram
Below daigram shows a system context daigram if we intend to build long running, scalable, fault Tolerant and Highly Available Webcrawler system
![Webcrawler system context diagram](/images/Webcrawler_system_context_diagram.png)


## Running it Locally
* Clone this project locally and import it as a gradle project in Intellij
* Run WebCrawlerMain class to see the Results

### Sample Output for Two webcrawlers visiting https://Monzo.com and https://revolut.com

```
################################ Printing Webcrawler Results ########################

No.of pages visited by webcrawler 1 : 10 
Pages visited by webcrawler 1 are : [https://monzo.com/, https://monzo.com/i/business/, https://monzo.com/i/business/business-switch/, https://monzo.com/i/business/help/, https://monzo.com/i/business/eligibility/, https://monzo.com/i/business/testimonials/, https://monzo.com/i/business, https://monzo.com/i/business/features/, https://monzo.com/-deeplinks/business_account_signup/, https://monzo.com/i/business/sign-up-for-business/]
Showing all visited Pages and Urls in each page for webcrawler 1
{https://monzo.com/=[#, /, /, /, /i/business, /i/current-account/, ...]}

 ################################ Printing Webcrawler Results ########################
 
No.of pages visited by webcrawler 2 : 10 
Pages visited by webcrawler 2 are : [https://revolut.com/revolut-premium, https://revolut.com/payments, https://revolut.com, https://revolut.com/introducing-revolut-metal, https://revolut.com/currency-converter, https://revolut.com/metal, https://revolut.com/revolut-plus, https://revolut.com/our-pricing-plans, https://revolut.com/app, https://revolut.com/a-radically-better-account]
Showing all visited Pages and Urls in each page for webcrawler 2
{https://revolut.com/revolut-premium=[/, https://app.revolut.com/start, https://revolut.com/app, ...]}

