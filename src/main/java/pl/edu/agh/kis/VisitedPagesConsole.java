package pl.edu.agh.kis;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by karl on 12/12/16.
 */
public class VisitedPagesConsole implements VisitedPages {
    ArrayList<URL> visitedURLList = new ArrayList<URL>();


    public void printURLs(){
        System.out.println("/////////////////////////////////////////////////////////////VISITED");

        for (URL element:
                visitedURLList) {
            System.out.println(element.toString());
        }
    }

    @Override
    public boolean pageAlreadyVisited(URL pageURL) {
        return visitedURLList.contains(pageURL);
    }

    @Override
    public void addVisitedPage(URL pageURL) {
        visitedURLList.add(pageURL);
    }
}
