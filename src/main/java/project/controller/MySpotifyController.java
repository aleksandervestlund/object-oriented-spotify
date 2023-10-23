package project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import project.model.IPlaylist;
import project.model.Library;
import project.model.Music;
import project.model.Playlist;
import project.model.Song;

public class MySpotifyController {
    /**
     * Beskrivelse av appen: Man skal ha mulighet til å legge inn en liste med forhåndsbestemte
     * sanger til sine egne spillelister. Skal ligne litt på Spotify, men med egen implementasjon og
     * egne regler. Grunnklasser: Music: Både "Song" og "Playlist" skal arve fra denne abstrakte
     * klassen. Inneholder navn og coverbilde. Dette er fordi man ikke skal kunne instansiere
     * "Music", og implementasjonen for begge klassene er like. Song: Sangene man kan legge til i
     * spillelistene. Skal ikke kunne endres fra applikasjonen. Skal inneholde artist, sangnavn,
     * albumnavn og coverbilde. IPlaylist: Interface som inneholder "getPlaylist()". Dette er for at
     * begge klassene skal kunne kalles på samme måte fra applikasjonen. Playlist: Spillelistene man
     * kan endre. Inneholder en liste med sanger, spillelistenavn og coverbilde. Library: Skal
     * inneholde alle spillelistene og en liste over alle de forhåndsbestemte "Song"-objektene.
     * Filbehandling: Man skal kunne eksportere spillelister fra "Playlist"-klassen. Disse skal
     * kunne importeres fra "Library"-klassen, hvor da den nye spillelisten legges inn i
     * biblioteket. Testing: Alle klassene skal testes. Her skal det sørges for at korrekte unntak
     * utløses og at riktig tilstand oppnås.
     */

    /**
     * Appen skal etterligne Spotify. Brukeren skal kunne velge fra en mengde med forhåndsbestemte
     * sanger ("Song"-objekter) som den skal kunne legge inn i ulike spillelister
     * ("Playlist"-objekter). Videre skal brukeren ha mulighet til å endre verdiene for både navn og
     * bilde på disse spillelistene fra applikasjonen.
     */

    /**
     * selected... brukes kun ved legging til av sanger. Dette er fordi JavaFX ikke tillater at man
     * har trykket på noe i to forskjellige ListViews. selectedSong må ikke være av typen "Song"
     * fordi metodene i "Playlist" tar inn parametere av typen "Music".
     */
    private Playlist selectedPlaylist;
    private Music selectedSong;

    private Library library;

    @FXML
    private Button buttonSongs;
    @FXML
    private TextField textFieldName;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private TextField textFieldPicturePath;
    @FXML
    private Button buttonRename;
    @FXML
    private Button buttonCreate;
    @FXML
    private Button buttonImport;
    @FXML
    private TextArea textArea;
    @FXML
    private Label labelSongs;
    @FXML
    private ListView<Music> listViewPlaylists;
    @FXML
    private Button buttonOpen;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonExport;
    @FXML
    private ListView<Music> listViewSongs;
    @FXML
    private Button buttonChangePicture;
    @FXML
    private Button buttonSelectPlaylist;
    @FXML
    private Button buttonSelectSong;

    /**
     * Legger inn sanger som skal være tilgjengelige. Legger inn tre eksempler på spillelister, hvor
     * to av de har ti forskjellige sanger hver, og den siste er tom. Gjør enkelte knapper
     * utilgjengelige, gir brukeren en velkommen-melding og oppdaterer ListView for både
     * spillelister og sanger.
     */
    @FXML
    public void initialize() {
        this.library = new Library();

        // Sangtitler
        final List<String> fyeo = Arrays.asList("For Whom the Bell Tolls", "Immortal", "Deja Vu",
                "Ville Mentality", "She's Mine Pt. 1", "Change", "Neighbors", "Foldin Clothes",
                "She's Mine Pt. 2", "4 Your Eyez Only");
        final List<String> aap = Arrays.asList("Enchanted Waterfall", "Pink Dolphin Sunset",
                "Midnight's Interlude", "The Color Violet", "Lavender Sunflower",
                "Ballad of a Badman", "Lady Of Namek", "Pluto's Last Comet", "'87 Stingray",
                "Hunt From Mercury", "Last Kiss Of Nebulon");
        final List<String> m = Arrays.asList("Snoozefest", "My Beloved", "STFU", "Dennis Rodman",
                "i'm thinking about horses", "nobody knows", "A Million Miles", "Wicked",
                "Rich White Girls", "Strip Club", "White Linen", "Gorgeous",
                "The Life Of A Troubadour");

        fyeo.forEach(s -> this.library.getSongsLibrary()
                .addSong(new Song(s, "forYourEyesOnly.jpg", "J. Cole", "4 Your Eyez Only")));
        aap.forEach(s -> this.library.getSongsLibrary()
                .addSong(new Song(s, "aloneAtProm.jpg", "Tory Lanez", "Alone At Prom")));
        m.forEach(s -> this.library.getSongsLibrary()
                .addSong(new Song(s, "mansionz.jpeg", "mansionz", "Mansionz")));

        final Random random = new Random();
        final List<Music> alleSanger =
                new ArrayList<>(this.library.getSongsLibrary().getPlaylist());
        List<Music> tmpAlleSanger = new ArrayList<>(alleSanger);

        final List<Music> saantSkjerSanger = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            saantSkjerSanger.add(tmpAlleSanger.remove(random.nextInt(tmpAlleSanger.size())));
        }

        tmpAlleSanger = new ArrayList<>(alleSanger);
        final List<Music> theHangoverCureSanger = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            theHangoverCureSanger.add(tmpAlleSanger.remove(random.nextInt(tmpAlleSanger.size())));
        }

        this.library.addPlaylist(new Playlist("Sånt skjer", "saantSkjer.jpg", saantSkjerSanger));
        this.library.addPlaylist(
                new Playlist("The Hangover Cure", "theHangoverCure.jpg", theHangoverCureSanger));
        this.library.addPlaylist(new Playlist("lite kreativ"));

        this.buttonRemove.setDisable(true);
        this.checkAddSong();
        this.checkNameButtons();
        this.setOutputArea("Welcome! Refrain from using ';' or ',' in textfields. Use arrow keys "
                + "to change playlist/song.");
        this.updateListView(this.library.getSongsLibrary(), this.listViewSongs);
        this.updatePlaylistView();
    }

    /**
     * Lar ListView-et vise album-/sangcoveret til venstre og navnet til høyre. Coveret har
     * størrelsen 50x50.
     * 
     * @param list Hvilken liste som skal vises i ListView-et
     * @param listView Hvilket ListView som skal oppdateres. Kan enten være for spillelister eller
     *        for sanger
     */
    @FXML
    public void updateListView(final IPlaylist iPlaylist, final ListView<Music> listView) {
        listView.setItems(FXCollections.observableArrayList(iPlaylist.getPlaylist()));
        listView.setCellFactory((ListView<Music> param) -> new ListCell<>() {
            final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Music item, boolean empty) {
                String text = "";
                if (empty) {
                    setGraphic(null);
                } else {
                    imageView
                            .setImage(new Image(
                                    this.getClass()
                                            .getResource("/project/view/coverart/"
                                                    + item.getPicturePath())
                                            .toString(),
                                    50, 50, false, true));
                    setGraphic(imageView);

                    if (item instanceof Song) {
                        text += "- " + item.getName() + " -\n" + ((Song) item).getArtist() + " ("
                                + ((Song) item).getAlbum() + ")";
                    } else if (item instanceof Playlist) { // Kunne vært else
                        text += item.getName();
                    }
                }

                setFont(new Font(11));
                setText(text);
            }
        });

        this.listViewPlaylists.getSelectionModel().clearSelection();
        this.listViewSongs.getSelectionModel().clearSelection();
    }

    /**
     * Kjører i prinsippet bare en annen funksjon, men denne linjen måtte blitt gjentatt ekstremt
     * mange ganger, så har valgt å standardisere den.
     */
    @FXML
    public void updatePlaylistView() {
        this.updateListView(this.library, this.listViewPlaylists);
    }

    /**
     * Setter tekstområdet under alle knappene.
     * 
     * @param text Teksten som skal vises i TextArea-et
     */
    @FXML
    public void setOutputArea(final String text) {
        this.textArea.setText(text);
    }

    /**
     * Skrur av knappene dersom brukeren ikke har kommet med gyldig input. Disse håndteres også i
     * metodene, men er for at brukeren skal vite at de ikke har mulighet til å utføre metodekallet.
     */
    @FXML
    public void checkNameButtons() {
        if (Music.validateString(this.textFieldName.getText())) {
            this.buttonRename.setDisable(false);
            this.buttonCreate.setDisable(false);
            this.buttonImport.setDisable(false);
        } else {
            this.buttonRename.setDisable(true);
            this.buttonCreate.setDisable(true);
            this.buttonImport.setDisable(true);
        }
    }

    /**
     * Skrur av "Add song"-knappen dersom det ikke er valgt både spilleliste som skal legges til i
     * og sang som skal legges til.
     */
    @FXML
    public void checkAddSong() {
        if (Objects.isNull(this.selectedPlaylist) || Objects.isNull(this.selectedSong)) {
            this.buttonAdd.setDisable(true);
        } else {
            this.buttonAdd.setDisable(false);
        }
    }

    /**
     * Velger å ikke tillate at valgt spilleliste er null. Dersom dette er tilfellet utløses et
     * unntak, så catch-modulen utløses.
     */
    @FXML
    public void onButtonSelectPlaylist() {
        final Music tmpSelectedPlaylist =
                this.listViewPlaylists.getSelectionModel().getSelectedItem();

        try {
            this.selectedPlaylist = (Playlist) Objects.requireNonNull(tmpSelectedPlaylist);
            this.checkAddSong();

            this.setOutputArea("Selected playlist '" + this.selectedPlaylist.getName() + "'.");
        } catch (final Exception e) {
            this.setOutputArea("Select a playlist.");
        }
    }

    @FXML
    public void onButtonSelectSong() {
        final Music tmpSelectedSong = this.listViewSongs.getSelectionModel().getSelectedItem();

        try {
            this.selectedSong = Objects.requireNonNull(tmpSelectedSong);
            this.checkAddSong();

            this.setOutputArea("Selected song '" + this.selectedSong.getName() + "'.");
        } catch (final Exception e) {
            this.setOutputArea("Select a song.");
        }
    }

    /**
     * Viser alle tilgjengelige sanger til brukeren.
     */
    @FXML
    public void onButtonSongs() {
        this.buttonSongs.setDisable(true);
        this.buttonRemove.setDisable(true);
        this.labelSongs.setText("Songs");
        this.updateListView(this.library.getSongsLibrary(), this.listViewSongs);

        this.setOutputArea("Displayed all songs.");
    }

    /**
     * Erstatter ListView-et som viser alle sangene med alle sangene som er i en spesifisert
     * spilleliste.
     */
    @FXML
    public void onButtonOpen() {
        final Playlist tmpSelectedPlaylist =
                (Playlist) this.listViewPlaylists.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(tmpSelectedPlaylist)) {
            this.labelSongs.setText(tmpSelectedPlaylist.getName());
            this.buttonSongs.setDisable(false);
            this.buttonRemove.setDisable(false);
            this.updateListView(tmpSelectedPlaylist, this.listViewSongs);

            this.setOutputArea("Opened playlist '" + tmpSelectedPlaylist.getName() + "'.");
        } else {
            this.setOutputArea("Select a playlist to be opened.");
        }
    }

    /**
     * Sletter en spesifisert spilleliste. Har try_catch i if-setningen for å skille på hva feilen
     * er, sånn at brukeren får konstruktiv feilmelding.
     */
    @FXML
    public void onButtonDelete() {
        final Playlist tmpSelectedPlaylist =
                (Playlist) this.listViewPlaylists.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(tmpSelectedPlaylist)) {
            try {
                this.library.removePlaylist(tmpSelectedPlaylist);
                this.updatePlaylistView();

                this.setOutputArea("Deleted playlist '" + tmpSelectedPlaylist.getName() + "'.");
            } catch (final Exception e) {
                this.setOutputArea("Playlist already deleted.");
            }
        } else {
            this.setOutputArea("Select a playlist to be deleted.");
        }
    }

    /**
     * Catch-modulen utløses kun dersom man ikke har valgt en spilleliste. Lagrer name-variabelen
     * midlertidig for å gjenbruke kode.
     */
    @FXML
    public void onButtonExport() {
        final Playlist tmpSelectedPlaylist =
                (Playlist) this.listViewPlaylists.getSelectionModel().getSelectedItem();

        try {
            Objects.requireNonNull(tmpSelectedPlaylist);
            tmpSelectedPlaylist.exportPlaylist();

            String name = tmpSelectedPlaylist.getName();
            this.setOutputArea("Exported playlist '" + name + "' as '" + name + ".txt'.");
        } catch (final Exception e) {
            this.setOutputArea("Select a playlist to be exported.");
        }
    }

    /**
     * Legger til valgt sang til valgt spilleliste. Eneste gang selectedPlaylist og selectedSong
     * brukes.
     */
    @FXML
    public void onButtonAdd() {
        if (!Objects.isNull(this.selectedPlaylist) && !Objects.isNull(this.selectedSong)) {
            try {
                this.selectedPlaylist.addSong(this.selectedSong);

                this.setOutputArea("Added song '" + this.selectedSong.getName() + "' to playlist '"
                        + this.selectedPlaylist.getName() + "'.");
            } catch (final Exception e) {
                this.setOutputArea("Song already in playlist.");
            }
        } else {
            this.setOutputArea("Select both a song to be added and a playlist to be added to.");
        }
    }

    @FXML
    public void onButtonRemove() {
        final String text = this.labelSongs.getText();

        if (!text.equals("Songs")) {
            final Playlist playlist = (Playlist) this.library.getPlaylist().stream()
                    .filter(p -> p.getName().equals(text)).findFirst().orElse(null);

            final Music song = this.listViewSongs.getSelectionModel().getSelectedItem();
            playlist.removeSong(song);
            this.updateListView(playlist, listViewSongs);

            this.setOutputArea(
                    "Removed '" + song.getName() + "' from '" + playlist.getName() + "'.");
        } else {
            this.setOutputArea("Open a playlist before attempting to remove it.");
        }
    }

    /**
     * Unntak utløses i try-blokken dersom setName kaster IllegalArgumentException. Dette skjer i
     * realiteten aldri grunnet at "Rename playlist"-knappen er utilgjengelig dersom ugyldig input
     * er lagt inn.
     */
    @FXML
    public void onButtonRename() {
        final Music tmpSelectedPlaylist =
                this.listViewPlaylists.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(tmpSelectedPlaylist)) {
            String oldName = tmpSelectedPlaylist.getName();
            String newName = this.textFieldName.getText();

            try {
                tmpSelectedPlaylist.setName(newName);
                this.updatePlaylistView();

                this.setOutputArea("Renamed playlist '" + oldName + "' as '" + newName + "'.");
            } catch (final Exception e) {
                this.setOutputArea("Invalid playlist name.");
            }
        } else {
            this.setOutputArea("Select a playlist to be renamed.");
        }
    }

    /**
     * Trenger ikke try_catch, fordi setPicturePath aldri utløser unntak fordi den håndterer feil
     * input på egenhånd.
     */
    @FXML
    public void onButtonChangePicture() {
        final Music tmpSelectedPlaylist =
                this.listViewPlaylists.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(tmpSelectedPlaylist)) {
            String oldPicture = tmpSelectedPlaylist.getPicturePath();
            String newPicture = this.textFieldPicturePath.getText();

            tmpSelectedPlaylist.setPicturePath(newPicture);
            this.updatePlaylistView();

            this.setOutputArea(
                    "Changed cover picture playlist for playlist '" + tmpSelectedPlaylist.getName()
                            + "' from '" + oldPicture + "' to '" + newPicture + "'.");
        } else {
            this.setOutputArea("Select a playlist to be renamed.");
        }
    }

    /**
     * Lager en ny spilleliste. Hadde ikke trengt å differensiere mellom konstruktørkallene grunnet
     * at setPicturePath hadde håndtert det på egenhånd, men valgte å gjøre denne forskjellen mer
     * tydelig, i tillegg til hvorfor jeg valgte å ha flere konstruktører.
     */
    @FXML
    public void onButtonCreate() {
        final String name = this.textFieldName.getText();

        try {
            final String picturePath = this.textFieldPicturePath.getText();

            if (picturePath.equals("")) {
                this.library.addPlaylist(new Playlist(name));
            } else {
                this.library.addPlaylist(new Playlist(name, picturePath));
            }

            this.updatePlaylistView();

            this.setOutputArea("Created new playlist '" + name + "'.");
        } catch (final Exception e) {
            this.setOutputArea("Invalid playlist name.");
        }
    }

    /**
     * Importerer en spilleliste til brukeren sitt bibliotek. Utløser catch-blokken dersom
     * importPlaylist utløser IOException.
     */
    @FXML
    public void onButtonImport() {
        try {
            final String name = this.textFieldName.getText();

            this.library.importPlaylist(name);
            this.updatePlaylistView();

            this.setOutputArea("Playlist '" + name + "' imported from '" + name + ".txt'.");
        } catch (final Exception e) {
            this.setOutputArea("Invalid playlist name.");
        }
    }

    public static void main(final String[] args) {
        MySpotifyController mySpotifyController = new MySpotifyController();
        mySpotifyController.initialize();
    }
}
