package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.model.IPlaylist;
import project.model.Library;
import project.model.Playlist;
import project.model.Song;

public class LibraryTest {
    Library l1;

    Playlist p1;
    Playlist p2;
    Playlist p3;

    Song s1;
    Song s2;

    @BeforeEach
    public void setUp() {
        this.s1 = new Song("My Fault", "Eminem", "The Eminem Show");
        this.s2 = new Song("Neighbors", "forYourEyesOnly.jpg", "J. Cole", "4 Your Eyez Only");

        this.p1 = new Playlist("Sånt skjer");
        this.p2 = new Playlist("Know 'Em All!");
        this.p3 = new Playlist("The Hangover Cure");

        this.l1 = new Library();
    }

    @Test
    public void testPolymorphy() {
        assertTrue(this.l1 instanceof Library);
        assertTrue(this.l1 instanceof IPlaylist);
    }

    @Test
    public void testGetPlaylist() {
        assertTrue(this.l1.getPlaylist().isEmpty());

        this.l1.addPlaylist(this.p1);
        assertEquals(this.p1, this.l1.getPlaylist().get(0));
        assertEquals(1, this.l1.getPlaylist().size());

        this.l1.addPlaylist(this.p2);
        assertEquals(this.p2, this.l1.getPlaylist().get(0));
        assertEquals(this.p1, this.l1.getPlaylist().get(1));
        assertEquals(2, this.l1.getPlaylist().size());

        this.l1.getPlaylist().add(this.p3);
        assertEquals(2, this.l1.getPlaylist().size());
    }

    @Test
    public void testAddPlaylist() {
        assertTrue(this.l1.getPlaylist().isEmpty());

        this.l1.addPlaylist(this.p1);
        assertEquals(this.p1, this.l1.getPlaylist().get(0));
        assertEquals(1, this.l1.getPlaylist().size());

        assertThrows(IllegalStateException.class, () -> this.l1.addPlaylist(this.p1));
        assertThrows(IllegalStateException.class, () -> this.l1.addPlaylist(this.s1));
        assertEquals(1, this.l1.getPlaylist().size());
    }

    @Test
    public void testRemovePlaylist() {
        assertTrue(this.l1.getPlaylist().isEmpty());

        this.l1.addPlaylist(this.p1);
        this.l1.addPlaylist(this.p2);
        assertEquals(2, this.l1.getPlaylist().size());

        this.l1.removePlaylist(this.p1);
        assertEquals(1, this.l1.getPlaylist().size());
        assertEquals(this.p2, this.l1.getPlaylist().get(0));

        assertThrows(IllegalStateException.class, () -> this.l1.removePlaylist(this.p1));
        assertEquals(1, this.l1.getPlaylist().size());
    }

    @Test
    public void testImportPlaylist() {
        assertEquals(0, this.l1.getPlaylist().size());

        try {
            this.p1.exportPlaylist();
            this.l1.importPlaylist(this.p1.getName());

            // Sletter filen etterpå
            new File("src/main/resources/project/view/playlists/" + this.p1.getName() + ".txt")
                    .delete();
        } catch (final Exception e) {
        }

        assertEquals(1, this.l1.getPlaylist().size());
        final Playlist p4 = (Playlist) this.l1.getPlaylist().get(0);

        assertEquals(this.p1.getName(), p4.getName());
        assertEquals(this.p1.getPicturePath(), p4.getPicturePath());
        assertEquals(this.p1.getPlaylist().size(), p4.getPlaylist().size());

        assertThrows(IOException.class, () -> this.l1.importPlaylist("xyzTest"));
    }
}
