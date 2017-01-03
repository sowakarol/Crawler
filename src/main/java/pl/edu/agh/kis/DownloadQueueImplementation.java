package pl.edu.agh.kis;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by karl on 12/11/16.
 */
public class DownloadQueueImplementation implements DownloadQueue {
    ArrayList<URL> urlArrayList = new ArrayList<URL>();

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/Crawler";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "asd";
    private Connection dbConnection;


    DownloadQueueImplementation(Connection dbConnection){
        this.dbConnection = dbConnection;
    }

    private void insertURL(URL pageURL){
        Statement stmt = null;

        try {

            stmt = dbConnection.createStatement();

            // uwaga - w tej wersji nie wstawiamy daty urodzenia

            stmt.executeUpdate("insert into ToVisit(pageURL) "

                    + "values( '" + pageURL.toString() + "')");

           // urlArrayList.add(pageURL);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public URL getNextPage() {
        return urlArrayList.remove(0);
    }

    @Override
    public void addPage(URL pageURL) {
        if(!urlArrayList.contains(pageURL)) {
            urlArrayList.add(pageURL);
            insertURL(pageURL);
        }
    }

    @Override
    public boolean isEmpty() {
        return urlArrayList.isEmpty();
    }
}
