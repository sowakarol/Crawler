package pl.edu.agh.kis;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by karl on 12/19/16.
 */
public class DownloadQueueConsoleTest {
    @Test
    public void printURLs() throws Exception {

    }

    @Test(expected = Exception.class)
    public void getNextPageIfCollectionIsEmpty() throws Exception {
        DownloadQueue downloadQueue = new DownloadQueueConsole();
        downloadQueue.getNextPage();

    }

    @Test
    public void addPageTest() throws Exception {
        DownloadQueue downloadQueue = new DownloadQueueConsole();
        downloadQueue.addPage(new URL("http://www.wp.pl"));
        assertEquals(downloadQueue.getNextPage(),new URL("http://www.wp.pl"));
    }

    @Test
    public void isEmptyTestIfIsEmpty() throws Exception {
        DownloadQueue downloadQueue = new DownloadQueueConsole();
        assertEquals(downloadQueue.isEmpty(), true);
    }
    @Test
    public void isEmptyTestIfIsNotEmpty() throws Exception {
        DownloadQueue downloadQueue = new DownloadQueueConsole();
        downloadQueue.addPage(new URL("http://www.wp.pl"));

        assertEquals(downloadQueue.isEmpty(), false);
    }

    @Test
    public void isEmptyTestIfIsEmptyAfterAddingAndRemoving() throws Exception {
        DownloadQueue downloadQueue = new DownloadQueueConsole();
        downloadQueue.addPage(new URL("http://www.wp.pl"));
        downloadQueue.getNextPage();
        assertEquals(downloadQueue.isEmpty(), true);
    }
}