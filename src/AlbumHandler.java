import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class AlbumHandler extends DefaultHandler {
    private boolean bName = false;
    private boolean bArtist = false;
    private boolean bContent = false;

    private String name;
    private String artist;
    private String genre;
    private XMLDownloadTask delegate;

    /**
     * AlbumHandler(): constructor
     * @param delegate
     */
    public AlbumHandler(XMLDownloadTask delegate) {
        this.delegate = delegate;
    }

    /**
     * startElement(): runs at start of specified XML element
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     */
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
        if (qName.equalsIgnoreCase("entry"))
            bContent = true;
        if (qName.equalsIgnoreCase("im:name")) {
            bName = true;
            name = "";
        }
        if (qName.equalsIgnoreCase("im:artist")) {
            bArtist = true;
            artist = "";
        }
        if (bContent & qName.equalsIgnoreCase("category")) {
            genre = attributes.getValue("term");
            bContent = false;
        }
    }

    /**
     * characters(): assigns elements from XML data
     * @param ch
     * @param start
     * @param length
     */
    public void characters(char ch[], int start, int length) {
        if (bName)
            name = name + new String(ch, start, length);
        if (bArtist)
            artist = artist + new String(ch, start, length);
    }

    /**
     * endElement(): runs at the end of specified XML element
     * @param uri
     * @param localName
     * @param qName
     */
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("im:name"))
            bName = false;
        if (qName.equalsIgnoreCase("im:artist"))
            bArtist = false;
        if (qName.equalsIgnoreCase("entry")) {
            Album album = new Album(name, artist, genre);
            delegate.getAlbums().add(album);
        }
    }
}
