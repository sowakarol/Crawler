package pl.edu.agh.kis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.edu.agh.kis.LoggingToFile;

/**
 * Created by karl on 12/11/16.
 */
abstract public class WebCrawler {
    DownloadQueue downloadQueue;
    VisitedPages visitedPages;
    WWWPageDownloader wwwPageDownloader;
    LoggingToFile log = new LoggingToFile();


    WebCrawler(DownloadQueue downloadQueue, VisitedPages visitedPages, WWWPageDownloader wwwPageDownloader){
        this.downloadQueue = downloadQueue;
        this.visitedPages = visitedPages;
        this.wwwPageDownloader = wwwPageDownloader;
    }



    boolean addStartWebsite(String firstWebsite){
        try {
            downloadQueue.addPage(new URL(firstWebsite));
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    URL getNextPage(){
        return downloadQueue.getNextPage();
    }


    boolean pageAlreadyVisited(URL toVisit){
        return visitedPages.pageAlreadyVisited(toVisit);
    }


    String downloadPage(String toDownload)
    {
        try {
            return wwwPageDownloader.downloadPage(toDownload);
        } catch (DownloaderException e) {
            e.printStackTrace();
            return null;
        }
    }
    void addVisitedPage(URL toAdd){
        visitedPages.addVisitedPage(toAdd);
    }

    String getContentFromPageIfIsNotVisited(){
        URL toVisit = getNextPage();
        //String toVisitString = toVisit.toString();
        /*String hrefRegex = "<[aA] [^>]*[hH][rR][eE][fF]=\"([^\"]+)\"";
        String httpRegex = "^http.*";

        Pattern p = Pattern.compile(hrefRegex);

        Matcher m = p.matcher(content);*/
        String hrefRegex = "<[aA] [^>]*[hH][rR][eE][fF]=\"([^\"]+)\"";
        String httpRegex = "^http.*";

        if(!pageAlreadyVisited(toVisit) && toVisit.toString().matches(httpRegex)){
            addVisitedPage(toVisit);

            return downloadPage(toVisit.getHost());
        } else {
            return "Page was already visited";
        }
    }


    protected ArrayList<String> findWebsites(String content){
        ArrayList<String> ret = new ArrayList<String>();
        //Pattern p = Pattern.compile("<[aA] [^>]*[hH][rR][eE][fF]=\"([^\"]+)\"");
        String hrefRegex = "<[aA] [^>]*[hH][rR][eE][fF]=\"([^\"]+)\"";
        String httpRegex = "^http.*";
        Pattern p = Pattern.compile(hrefRegex);

        Matcher m = p.matcher(content);

        while(m.find()){
            try {
                String hrefFound = m.group(1);
                if(hrefFound.matches(httpRegex)) {
                    if(!pageAlreadyVisited(new URL(hrefFound))) {
                        ret.add(hrefFound);
                        log.info(m.group(1));

                    }
                }


            } catch (MalformedURLException e) {
                log.warning(e.toString());
                //e.printStackTrace();
            }
        }
        return ret;
    }


    void crawl() throws Exception {
        addStartWebsite("http://kis.agh.edu.pl");
        int i = 0;
        while(!downloadQueue.isEmpty() && i < 100){
            String content = getContentFromPageIfIsNotVisited();

            if(content != null) {
                ArrayList<String> finded = findWebsites(content);
                for (String element : finded) {
                    if(element.length() < 150) {
                        try {
                            if(!pageAlreadyVisited(new URL(element))) {

                                downloadQueue.addPage(new URL(element));
                                log.print();
                            }
                        } catch (MalformedURLException e) {
                            log.warning(e.toString());
                            log.print();
                        }
                    }
                }
            }
            i++;
        }
    }


}
