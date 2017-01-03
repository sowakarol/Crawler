package pl.edu.agh.kis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by karl on 12/19/16.
 */
public class WebCrawlerMultithreading extends WebCrawler implements Runnable {
    String content;
    ArrayList<Thread> threads = new ArrayList<>();

    /*   static class WebCrawlerSingleThread extends WebCrawler implements Runnable{
        WebCrawlerSingleThread(DownloadQueue downloadQueue, VisitedPages visitedPages, WWWPageDownloader wwwPageDownloader) throws Exception {
            super(downloadQueue, visitedPages, wwwPageDownloader);

        }

        public void run() {
            try {
                String content = getContentFromPageIfIsNotVisited();
                if (content != null) {
                    ArrayList<String> finded = findWebsites(content);
                    for (String element : finded) {
                        if (element.length() < 150) {
                            try {
                                if (!pageAlreadyVisited(new URL(element))) {

                                    try {
                                        // pobranie nastepnej wartosci z kolejki - UWAGA PROBLEMY!
                                        downloadQueue.addPage(new URL(element));
                                        log.print();
                                    } catch (IndexOutOfBoundsException e) {
                                        continue;
                                    }

                                }
                            } catch (MalformedURLException e) {
                                log.warning(e.toString());
                                log.print();
                                continue;
                            }
                        }
                    }
                }
            } catch(Exception e){

            }
        }

        public void crawl() throws Exception {
            addStartWebsite("http://kis.agh.edu.pl");
            int i = 0;
            ArrayList<Thread> threads = new ArrayList<>();
            while (!downloadQueue.isEmpty() && i < 100) {
                WebCrawlerSingleThread webCrawlerThread = new WebCrawlerSingleThread(new DownloadQueueConsole(), new VisitedPagesConsole(), new WWWPageDownloaderImplementation());
                Thread crawlThread = new Thread(webCrawlerThread);
                threads.add(crawlThread);

                for(Thread t: threads){
                    t.join();
                }
                crawlThread.start();
                i++;
            }

        }


    }
*/
    public void run() {
        while (true) {
            try {
                if (content != null) {
                    ArrayList<String> finded = findWebsites(content);
                    for (String element : finded) {
                        if (element.length() < 150) {
                            try {
                                if (!pageAlreadyVisited(new URL(element))) {
                                    try {
                                        downloadQueue.addPage(new URL(element));
                                        log.print();
                                    } catch (IndexOutOfBoundsException e) {
                                        e.printStackTrace();

                                    }
                                }
                            } catch (MalformedURLException e) {
                                log.warning(e.toString());
                                log.print();

                            }
                        }
                    }
                } else {
                    System.out.println("content = null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    WebCrawlerMultithreading(DownloadQueue downloadQueue, VisitedPages visitedPages, WWWPageDownloader wwwPageDownloader, String content) throws Exception {
        super(downloadQueue, visitedPages, wwwPageDownloader);
        System.out.println("content: " + content);
        this.content = content;

    }


    public void crawl() throws Exception {
        addStartWebsite("http://kis.agh.edu.pl");
        int i = 0;
//        ArrayList<Thread> threads = new ArrayList<>();
        while (!downloadQueue.isEmpty() && i < 1000) {
            String content = getContentFromPageIfIsNotVisited();
            if (content != null) {
                ArrayList<String> finded = findWebsites(content);
                for (String element : finded) {
                    if(threads.size() < 20) {
                        WebCrawlerMultithreading webCrawlerThread = new WebCrawlerMultithreading(this.downloadQueue,
                                this.visitedPages, this.wwwPageDownloader, element);
                        Thread crawlThread = new Thread(webCrawlerThread);
                        threads.add(crawlThread);

                    /*for (Thread t : threads) {
                        t.join();
                    }*/
                        crawlThread.start();
                    }
                }
                /*for (Thread t : threads) {
                    t.join();
                }*/

            } else {
                threads.remove(this);
            }
            i++;
        }
    }

    public static void main(String[] args) throws Exception {
        WebCrawlerMultithreading webCrawlerMultithreading = new WebCrawlerMultithreading(
                new DownloadQueueConsole(),
                new VisitedPagesConsole(),
                new WWWPageDownloaderImplementation(),
                ""
        );
        webCrawlerMultithreading.crawl();
    }
}
