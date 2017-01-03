package pl.edu.agh.kis;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by karl on 12/13/16.
 */
public class WWWPageDownloaderImplementationTest {

    @Test
    public void downloadPage() throws Exception {


    }

 /*   @Test(expected = Exception.class)
    public void check404(){
        WWWPageDownloader wwwPageDownloader = new WWWPageDownloaderImplementation();
        try {
            wwwPageDownloader.downloadPage("student.agh.edu.pl/~karosowa/NIEMATAKIEGOPLIKU.html");
        } catch (DownloaderException e) {

            e.printStackTrace();
        }


    }*/

    @Test(expected = Exception.class)
    public void checkBadURL(){
        WWWPageDownloader wwwPageDownloader = new WWWPageDownloaderImplementation();
        try {
            wwwPageDownloader.downloadPage("złyURL");
        } catch (DownloaderException e) {
            e.printStackTrace();
        }
    }

}