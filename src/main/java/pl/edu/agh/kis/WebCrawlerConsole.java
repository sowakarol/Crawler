package pl.edu.agh.kis;

/**
 * Created by karl on 12/12/16.
 */
public class WebCrawlerConsole extends WebCrawler {
    WebCrawlerConsole(){
        super(new DownloadQueueConsole(), new VisitedPagesConsole(), new WWWPageDownloaderImplementation());
    }



    public static void main(String[] args) {
        WebCrawlerConsole webCrawlerConsole = new WebCrawlerConsole();

        try {
            webCrawlerConsole.crawl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
