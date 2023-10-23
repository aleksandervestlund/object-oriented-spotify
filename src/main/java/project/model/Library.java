package project.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Library implements IPlaylist {
    /**
     * Gjenbruker funksjonalitet fra "Playlist", siden et bibliotek bare er en ekstremt stor
     * spilleliste med alle mulige sanger.
     */
    private final Playlist songsLibrary = new Playlist("Songslibrary");

    // Bruker ikke konstruktør, men lager heller bare et tomt bibliotek hver gang
    private final List<Music> playlists = new ArrayList<>();

    public Playlist getSongsLibrary() {
        return this.songsLibrary;
    }

    /**
     * Lar ikke en spilleliste ha duplikater av "Playlist"-objekter. Sorterer etter spillelisten er
     * lagt til. Stopper metoden dersom parameteret er ugyldig.
     * 
     * @param playlist Hvilken spilleliste som skal legges til
     * @throws IllegalStateException Hvis ugyldig input
     */
    public void addPlaylist(final Music playlist) {
        if (this.playlists.contains(playlist) || !(playlist instanceof Playlist))
            throw new IllegalStateException();

        this.playlists.add(playlist);
        this.sortDefault();
    }

    /**
     * Ikke mulig å fjerne et objekt som ikke finnes i listen fra før av.
     * 
     * @param playlist
     * @throws IllegalStateException Hvis listen ikke er i biblioteket fra før av
     */
    public void removePlaylist(final Music playlist) {
        if (!this.playlists.contains(playlist))
            throw new IllegalStateException();

        this.playlists.remove(playlist);
    }

    // Returnerer ny liste for å ha riktig innkapsling.
    public List<Music> getPlaylist() {
        return new ArrayList<>(this.playlists);
    }

    /**
     * Filen er på formatet "xxx;xxx;yyy,yyy,yyy,yyy;..." Første "xxx" er navn. Andre "xxx" er
     * bilde. Alle "yyy,yyy,yyy,yyy" er sanger. Første "yyy" er navn. Andre "yyy" er bilde. Tredje
     * "yyy" er artist. Fjerde "yyy" er album. Leser en spilleliste fra fil, og legger deretter til
     * denne i dette "Library"-objektet. Dersom spillelisten ikke har noen sanger, går dette fint.
     * 
     * @param filename Navnet på spillelisten som skal importeres
     * @throws IOException Dersom spillelisten aldri har blitt eksportert
     */
    public void importPlaylist(final String filename) throws IOException {
        final Scanner myReader = new Scanner(
                new File("src/main/resources/project/view/playlists/" + filename + ".txt"));
        final List<String> datas = new ArrayList<>(Arrays.asList(myReader.nextLine().split(";")));
        myReader.close();

        final String name = datas.remove(0);
        final String picturePath = datas.remove(0);
        final List<Music> newSongs = new ArrayList<>();

        for (final String song : datas) {
            final String[] values = song.split(",");
            newSongs.add(new Song(values[0], values[1], values[2], values[3]));
        }

        this.addPlaylist(new Playlist(name, picturePath, newSongs));
    }

    // Sorterer i stigende rekkefølge; først på spillelistenavn, og deretter på størrelse
    private void sortDefault() {
        this.playlists.sort((s1, s2) -> {
            final int diff = s1.getName().compareToIgnoreCase(s2.getName());

            if (diff != 0)
                return diff;

            return Integer.compare(((Playlist) s1).getPlaylist().size(),
                    ((Playlist) s2).getPlaylist().size());
        });
    }
}
