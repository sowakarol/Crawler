package pl.edu.agh.kis;

import java.io.*;
import java.net.Socket;

/**
 * Created by karl on 12/11/16.
 */
public class WWWPageDownloaderImplementation implements WWWPageDownloader {

    static private String parsePageURL(String pageURL){
        if(pageURL.contains("www.")){
            return pageURL.replace("www.", "");
        }
        return pageURL;
    }

    @Override
    public String downloadPage(String pageURL) throws DownloaderException {
        StringBuilder ret = new StringBuilder();
        Socket s = null;

        pageURL = parsePageURL(pageURL);
        System.out.println(pageURL);
        try {
            s = new Socket(pageURL, 80);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nie udało się połaczyć");
        }
        try {

            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            PrintWriter p = new PrintWriter(os);

            p.write("GET / HTTP/1.1 \r\n");
            p.write("Host: " + pageURL + "\r\n");
            p.write("\r\n\n");
            p.flush();



            String line = br.readLine();
            ret.append(line);
            while ((line = br.readLine()) != null) {
                ret.append(line + "\r\n");
            }

            return ret.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "PAGE NOT FOUND";
    }
}
