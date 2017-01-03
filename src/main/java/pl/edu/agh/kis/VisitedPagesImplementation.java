package pl.edu.agh.kis;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by karl on 12/12/16.
 */
public class VisitedPagesImplementation implements VisitedPages {
    ArrayList<URL> visitedURLList = new ArrayList<URL>();

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/Crawler";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "asd";
    private Connection dbConnection;


    VisitedPagesImplementation(Connection dbConnection){
        this.dbConnection = dbConnection;
    }


    private void insertURL(URL pageURL){
        Statement stmt = null;

        try {

            stmt = dbConnection.createStatement();

            stmt.executeUpdate("insert into Visited (pageURL) "

                    + "values( '" + pageURL.toString() + "')");

           // visitedURLList.add(pageURL);

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
    public boolean pageAlreadyVisited(URL pageURL) {
        return visitedURLList.contains(pageURL);
    }

    @Override
    public void addVisitedPage(URL pageURL) {
        insertURL(pageURL);

        visitedURLList.add(pageURL);
    }
}
