package project.model;

import java.util.List;

public interface IPlaylist {
    /**
     * Grunnen til at jeg valgte at både "Playlist" og "Library" skulle implementere grensesnittet
     * "IPlaylist", er fordi begge inneholder en liste med "Music"-objekter, og disse skal kalles på
     * samme måte fra applikasjonen.
     */

    // Implisitt public fordi det er interface
    List<Music> getPlaylist();
}
