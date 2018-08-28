import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XMLDownloadTask extends SwingWorker<List<Album>, Album> {

    private String urlString;
    private XMLDownloadPanel delegate;
    private ArrayList<Album> albums;
    private String xmlString;

    /**
     * XMLDownloadTask(): constructor
     * @param urlString
     * @param delegate
     */
    public XMLDownloadTask(String urlString, XMLDownloadPanel delegate) {
        this.urlString = urlString;
        this.delegate = delegate;
        albums = new ArrayList<>();
    }

    /**
     * doInBackground(): connect to URL, get XML data, parse, and display
     * @return
     */
    public List<Album> doInBackground() {
        HttpURLConnection connection = null;
        try {
            // Create a URL object from a String that contains a valid URL
            URL url = new URL(urlString);
            // Open an HTTP connection for the URL
            connection = (HttpURLConnection) url.openConnection();
            // Set HTTP request method
            connection.setRequestMethod("GET");
            // If the HTTP status code is 200, we have successfully connected
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Use a mutable StringBuilder to store the downloaded text
                StringBuilder xmlResponse = new StringBuilder();
                // Create a BufferedReader to read the lines of XML from the
                // connection's input stream
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                // Read lines of XML and append them to the StringBuilder
                // object until the end of the stream is reached
                String strLine;
                while ((strLine = input.readLine()) != null) {
                    xmlResponse.append(strLine);
                }
                // Convert the StringBuilder object to a String
                xmlString = xmlResponse.toString();
                /**
                 * Do something to process the XML in xmlString
                 */
                // Close the input stream
                input.close();
            }
        } catch (MalformedURLException e) {
            // Handle MalformedURLException
        } catch (IOException e) {
            // Handle IOException
        } catch (Exception e) {
            // Handle any other exceptions thrown by the code that
            // processes xmlString
        } finally {
            // close connection
            if (connection != null) {
                connection.disconnect();
            }
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
            saxParser.parse(new InputSource(new ByteArrayInputStream(
                    xmlString.getBytes("utf-8"))), new AlbumHandler(this));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        // display on JTextArea
        for (Album album : albums) {
            delegate.displayAlbum(album);
        }
        return albums;
    }

    /**
     * getAlbums(): getter method for albums ArrayList
     * @return
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }
}