package project.model;

import java.io.File;
import java.util.Objects;

public abstract class Music {
    /**
     * Grunnen til at jeg valgte at både "Playlist" og "Song" skulle arve fra den abstrakte klassen
     * "Music" (extends Music), er fordi implementasjonen av sang-/spillelistenavn har lik
     * implementasjon.
     */

    /**
     * Feltene trenger ikke være protected selv om det er super-klasse. Bruker heller
     * setter-metodene fra sub-klassene.
     */
    private String name;
    private String picturePath;

    /**
     * Abstrakt metode. Må defineres i alle subklasser for korrekt eksportering.
     * 
     * @return Streng som skal skrives i "exportPlaylist" i sub-klassen "Playlist"
     */
    public abstract String soutMusic();

    /**
     * Klasse konstruktør. Bruker settere for å ha riktig validering.
     * 
     * @param name Navnet på musikken
     * @param picturePath Coverbildet for musikken
     */
    public Music(final String name, final String picturePath) {
        this.setName(name);
        this.setPicturePath(picturePath);
    }

    public String getName() {
        return this.name;
    }

    /**
     * Validerer om strengen er gyldig. Kan ikke inneholde ";" eller "," grunnet eksportering.
     * Hjelpemetoder pleier å være private, men denne brukes også i "controller"-en, så den er
     * public.
     * 
     * @param string Streng som skal valideres
     * @return "boolean"-verdi for om string er gyldig
     */
    public static boolean validateString(final String string) {
        return !(string.contains(";") || string.contains(",") || string.equals("")
                || Objects.isNull(string));
    }

    /**
     * Stopper instansieringen av objektet dersom navnet er ugyldig.
     * 
     * @param name Navn som skal settes
     */
    public void setName(final String name) {
        if (!validateString(name))
            throw new IllegalArgumentException();

        this.name = name;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    /**
     * Setter bildet som spesifisert. Dersom filstien er ugyldig settes den til en standardverdi som
     * avhenger av hvilken sub-klasse metoden kalles fra.
     * 
     * @param picturePath
     */
    public void setPicturePath(final String picturePath) {
        if (validateString(picturePath)
                && new File("src/main/resources/project/view/coverart/" + picturePath).exists()) {
            this.picturePath = picturePath;
        } else if (this instanceof Playlist) {
            this.picturePath = Playlist.DEFAULTPLAYLISTURL;
        } else if (this instanceof Song) {
            this.picturePath = Song.DEFAULTSONGURL;
        }
    }

    // Teksten som vises i applikasjonen.
    @Override
    public String toString() {
        return this.getName();
    }
}
