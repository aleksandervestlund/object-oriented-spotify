package project.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Playlist extends Music implements IPlaylist {
    // Standardverdi o0pådersom det er oppgitt ugyldig bilde.
    public static final String DEFAULTPLAYLISTURL = "defaultPlaylist.png";

    private final List<Music> songs;

    /**
     * Klasse konstruktør. Lager en kopi av "songs"-samlingen
     * 
     * @param name Spillelistens navn
     * @param picturePath Spillelistens coverbilde
     * @param songs Forhåndsdefinerte sanger som skal være i listen
     */
    public Playlist(final String name, final String picturePath, final Collection<Music> songs) {
        super(name, picturePath);
        this.songs = new ArrayList<>(songs);
        this.sortDefault();
    }

    /**
     * Klasse konstruktør. Tom spilleliste med spesifisert bilde. Kaller den mest "omfattende"
     * konstruktøren.
     * 
     * @param name
     * @param picturePath
     */
    public Playlist(final String name, final String picturePath) {
        this(name, picturePath, Collections.emptyList());
    }

    /**
     * Klasse konstruktør. Spilleliste uten definert bilde. Unødvendig med innkapsling av "songs"
     * 
     * @param name
     * @param songs
     */
    public Playlist(final String name, final Collection<Music> songs) {
        this(name, DEFAULTPLAYLISTURL, songs);
    }

    /**
     * Klasse konstruktør. Tom spilleliste. Kunne kalt den mest "omfattende" konstruktøren, men
     * ønsker å gjenbruke kode.
     * 
     * @param name
     */
    public Playlist(final String name) {
        this(name, DEFAULTPLAYLISTURL);
    }

    /**
     * Legger til sangen i "songs" dersom den ikke er der fra før. Sorterer deretter spillelisten.
     * Dersom sangen er ugyldig, stoppes metoden.
     * 
     * @param song Sangen som skal legges til
     */
    public void addSong(final Music song) {
        if (this.songs.contains(song) || !(song instanceof Song))
            throw new IllegalStateException();

        this.songs.add(song);
        this.sortDefault();
    }

    /**
     * Trenger ikke sortere dersom man fjerner en sang.
     * 
     * @param song Sangen som skal fjernes
     */
    public void removeSong(final Music song) {
        if (!this.songs.contains(song))
            throw new IllegalStateException();

        this.songs.remove(song);
    }

    // Returnerer kopi av spillelisten istedenfor selve spillelisten for riktig innkapsling.
    public List<Music> getPlaylist() {
        return new ArrayList<>(this.songs);
    }

    // Sorterer sanger i stigende rekkefølge; først på sangnavn, så på artist, så på album.
    private void sortDefault() {
        this.songs.sort((s1, s2) -> {
            int diff = s1.getName().compareToIgnoreCase(s2.getName());
            if (diff != 0)
                return diff;

            diff = ((Song) s1).getArtist().compareToIgnoreCase(((Song) s2).getArtist());
            if (diff != 0)
                return diff;

            return ((Song) s1).getAlbum().compareToIgnoreCase(((Song) s2).getAlbum());
        });
    }

    /**
     * Lager ny fil hvis den ikke eksisterer fra før av. "this" kaller toString for dette objektet.
     * 
     * @throws IOException Hvis filen ikke finnes fra før, så vil aldri kastes her
     */
    public void exportPlaylist() throws IOException {
        String filepath = "src/main/resources/project/view/playlists/" + this + ".txt";
        File file = new File(filepath);
        file.createNewFile();

        FileWriter myWriter = new FileWriter(filepath);
        myWriter.write(this.soutMusic());
        myWriter.close();
    }

    /**
     * Inneholder alltid minst to strenger separert av én ";". Alle videre separerte strenger er
     * sanger.
     */
    public String soutMusic() {
        String string = this.getName() + ";" + this.getPicturePath();

        for (final Music music : this.songs) {
            string += ";" + music.soutMusic();
        }

        return string;
    }
}
