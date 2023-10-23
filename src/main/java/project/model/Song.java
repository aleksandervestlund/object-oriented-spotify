package project.model;

public class Song extends Music {
    // Standardverdi dersom det er oppgitt ugyldig bilde
    public static final String DEFAULTSONGURL = "defaultSong.png";

    private String artist;
    private String album;

    /**
     * Klasse konstruktør. Kaller konstruktøren til super-klassen "Music", men bruker egne settere
     * for lokale felter.
     * 
     * @param name Navnet på sangen
     * @param picturePath Coverbilde for sangen
     * @param artist Sangens artist
     * @param album Albummet sangen tilhører
     */
    public Song(final String name, final String picturePath, final String artist,
            final String album) {
        super(name, picturePath);
        this.setArtist(artist);
        this.setAlbum(album);
    }

    /**
     * Klasse konstruktør. Kalles dersom ingen bildesti oppgis. Kaller mest "omfattende"
     * konstruktør.
     * 
     * @param name
     * @param artist
     * @param album
     */
    public Song(final String name, final String artist, final String album) {
        this(name, DEFAULTSONGURL, artist, album);
    }

    public String getArtist() {
        return this.artist;
    }

    /**
     * Bruker validateString fra super. Stopper instansieringen av objektet hvis det er ugyldig
     * navn.
     * 
     * @param artist Sangens artist
     */
    public void setArtist(final String artist) {
        if (!validateString(artist))
            throw new IllegalArgumentException();

        this.artist = artist;
    }

    public String getAlbum() {
        return this.album;
    }

    /**
     * @param album Sangens album
     */
    public void setAlbum(final String album) {
        if (!validateString(album))
            throw new IllegalArgumentException();

        this.album = album;
    }

    // Sangens eksporterte streng består alltid av fire strenger separert av tre ",".
    @Override
    public String soutMusic() {
        return this.getName() + "," + this.getPicturePath() + "," + this.getArtist() + ","
                + this.getAlbum();
    }
}
