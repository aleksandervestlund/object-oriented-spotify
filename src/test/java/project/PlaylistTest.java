package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.model.IPlaylist;
import project.model.Music;
import project.model.Playlist;
import project.model.Song;

public class PlaylistTest {
    private Playlist p1;
    private Playlist p2;
    private Playlist p3;
    private Playlist p4;

    private Song s1;
    private Song s2;
    private Song s3;

    @BeforeEach
    public void setUp() {
        this.s1 = new Song("Superman", "Eminem", "The Eminem Show");
        this.s2 = new Song("Neighbors", "J. Cole", "4 Your Eyez Only");
        this.s3 = new Song("A.D.H.D", "Kendrick Lamar", "Section.80");

        this.p1 = new Playlist("Sånt skjer (Test)");
        this.p2 = new Playlist("Know 'Em All! (Test)", List.of(this.s1));
        this.p3 = new Playlist("The Hangover Cure (Test)", "xxx.png");
        this.p4 = new Playlist("White Noise (Test)", "forYourEyesOnly.jpg", List.of(this.s3));
    }

    @Test
    public void testConstructors() {
        assertTrue(this.p1 instanceof Playlist);
        assertTrue(this.p1 instanceof Music);
        assertTrue(this.p1 instanceof IPlaylist);
        assertEquals("Sånt skjer (Test)", this.p1.getName());
        assertEquals("defaultPlaylist.png", this.p1.getPicturePath());
        assertEquals(0, this.p1.getPlaylist().size());

        assertEquals("Know 'Em All! (Test)", this.p2.getName());
        assertEquals("defaultPlaylist.png", this.p2.getPicturePath());
        assertEquals(1, this.p2.getPlaylist().size());
        assertEquals(this.s1, this.p2.getPlaylist().get(0));

        assertEquals("The Hangover Cure (Test)", this.p3.getName());
        assertEquals("defaultPlaylist.png", this.p3.getPicturePath());
        assertEquals(0, this.p3.getPlaylist().size());

        assertEquals("White Noise (Test)", this.p4.getName());
        assertEquals("forYourEyesOnly.jpg", this.p4.getPicturePath());
        assertEquals(1, this.p4.getPlaylist().size());
        assertEquals(this.s3, this.p4.getPlaylist().get(0));
    }

    @Test
    public void testSetName() {
        assertEquals("Sånt skjer (Test)", this.p1.getName());
        this.p1.setName("syre");
        assertEquals("syre", this.p1.getName());

        assertThrows(IllegalArgumentException.class, () -> this.p1.setName(""));
        assertThrows(IllegalArgumentException.class, () -> this.p1.setName("a;a"));
        assertThrows(IllegalArgumentException.class, () -> this.p1.setName("a,a"));
        assertThrows(NullPointerException.class, () -> this.p1.setName(null));
    }

    @Test
    public void testSetPicturePath() {
        assertEquals("defaultPlaylist.png", this.p1.getPicturePath());
        this.p1.setPicturePath("forYourEyesOnly.jpg");
        assertEquals("forYourEyesOnly.jpg", this.p1.getPicturePath());

        this.p1.setPicturePath("forYourEyezOnly.jpg");
        assertEquals("defaultPlaylist.png", this.p1.getPicturePath());
    }

    @Test
    public void testAddSong() {
        assertEquals(0, this.p1.getPlaylist().size());
        this.p1.addSong(this.s1);
        assertEquals(this.s1, this.p1.getPlaylist().get(0));

        assertThrows(IllegalStateException.class, () -> this.p1.addSong(this.s1));
        assertThrows(IllegalStateException.class, () -> this.p1.addSong(this.p2));
        assertEquals(1, this.p1.getPlaylist().size());
    }

    @Test
    public void testRemoveSong() {
        assertEquals(this.s1, this.p2.getPlaylist().get(0));
        this.p2.removeSong(this.s1);
        assertTrue(this.p2.getPlaylist().isEmpty());

        this.p2.addSong(this.s2);
        assertThrows(IllegalStateException.class, () -> this.p1.removeSong(this.s1));
    }

    @Test
    public void testSortDefault() {
        assertEquals(this.s1, this.p2.getPlaylist().get(0));
        assertEquals(1, this.p2.getPlaylist().size());

        this.p2.addSong(this.s2);
        assertEquals(this.s2, this.p2.getPlaylist().get(0));
        assertEquals(this.s1, this.p2.getPlaylist().get(1));

        this.p2.addSong(this.s3);
        assertEquals(this.s3, this.p2.getPlaylist().get(0));
        assertEquals(this.s2, this.p2.getPlaylist().get(1));
        assertEquals(this.s1, this.p2.getPlaylist().get(2));
    }

    @Test
    public void testSoutMusic() {
        this.p4.addSong(this.s1);
        assertEquals("Sånt skjer (Test);defaultPlaylist.png", this.p1.soutMusic());
        assertEquals(
                "White Noise (Test);forYourEyesOnly.jpg;A.D.H.D,defaultSong.png,Kendrick "
                        + "Lamar,Section.80;Superman,defaultSong.png,Eminem,The Eminem Show",
                this.p4.soutMusic());
    }

    @Test
    public void testExportPlaylist() {
        this.p4.addSong(this.s2);

        try {
            this.p4.exportPlaylist();
        } catch (final Exception e) {
        }

        final File file =
                new File("src/main/resources/project/view/playlists/" + this.p4.getName() + ".txt");
        assertTrue(file.exists());

        // Sletter filen etterpå
        file.delete();
        assertFalse(file.exists());
    }
}
