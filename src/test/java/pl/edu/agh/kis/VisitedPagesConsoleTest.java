package pl.edu.agh.kis;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by karl on 12/19/16.
 */
public class VisitedPagesConsoleTest {
    @Test
    public void printURLs() throws Exception {

    }

    @Test
    public void pageAlreadyVisitedIfWasNotVisited() throws Exception {
        VisitedPages visitedPages = new VisitedPagesConsole();
        assertEquals(visitedPages.pageAlreadyVisited(new URL("http://www.wp.pl")), false);
    }

    @Test
    public void addVisitedPage() throws Exception {
        VisitedPages visitedPages = new VisitedPagesConsole();
        visitedPages.addVisitedPage(new URL("http://www.wp.pl"));

        assertEquals(visitedPages.pageAlreadyVisited(new URL("http://www.wp.pl")), true);
    }

}