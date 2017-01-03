package pl.edu.agh.kis;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by karl on 12/12/16.
 */
public class DownloadQueueConsole implements DownloadQueue{
    ArrayList<URL> urlArrayList = new ArrayList<URL>();


    public void printURLs(){
        System.out.println("/////////////////////////////////////////////////////////////TO BE VISITED");
        for (URL element:
             urlArrayList) {
            System.out.println(element.toString());
        }
    }


    @Override
    public URL getNextPage() {
        return urlArrayList.remove(0);
    }

    @Override
    public void addPage(URL pageURL) {
        urlArrayList.add(pageURL);
    }

    @Override
    public boolean isEmpty() {
        return urlArrayList.isEmpty();
    }
}
