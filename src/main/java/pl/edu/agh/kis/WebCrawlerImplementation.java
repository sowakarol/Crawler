package pl.edu.agh.kis;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by karl on 12/11/16.
 */
public class WebCrawlerImplementation extends WebCrawler {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/Crawler";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "asd";



    WebCrawlerImplementation(DownloadQueue downloadQueue, VisitedPages visitedPages, WWWPageDownloader wwwPageDownloader) throws Exception {
        super(downloadQueue,visitedPages,wwwPageDownloader);

    }

    public static void main(String[] args) throws Exception {

        Class.forName("org.h2.Driver");

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);

        WebCrawlerImplementation webCrawlerImplementation = new WebCrawlerImplementation(
                new DownloadQueueImplementation(conn),
                new VisitedPagesImplementation(conn),
                new WWWPageDownloaderImplementation()
        );

        webCrawlerImplementation.crawl();


        conn.close();
    }

}
