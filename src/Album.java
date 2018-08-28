public class Album {
    private String name;
    private String artist;
    private String genre;

    /**
     * Album(): constructor
     * @param name
     * @param artist
     * @param genre
     */
    public Album(String name, String artist, String genre) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
    }

    /**
     * getName(): getter method for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getArtist(): getter method for artist
     * @return
     */
    public String getArtist() {
        return artist;
    }

    /**
     * getGenre(): getter method for genre
     * @return
     */
    public String getGenre() {
        return genre;
    }

    /**
     * toString(): return contents of Album as a single String
     * @return
     */
    @Override
    public String toString() {
        String albumString;
        albumString = name + "; " + artist + "; " + genre;
        return albumString;
    }

    /**
     * toList(): returns contents of Album as a String array
     * @return
     */
    public String[] toList() {
        return new String[] {name, artist, genre};
    }
}
