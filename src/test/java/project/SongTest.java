package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.model.Music;
import project.model.Song;

public class SongTest {
    Song s1;
    Song s2;
    Song s3;

    @BeforeEach
    public void setUp() {
        this.s1 = new Song("My Fault", "Eminem", "The Eminem Show");
        this.s2 = new Song("Neighbors", "forYourEyesOnly.jpg", "J. Cole", "4 Your Eyez Only");
        this.s3 = new Song("A.D.H.D", "xyzTest.jpg", "Kendrick Lamar", "Section.80");
    }

    @Test
    public void testConstructors() {
        assertTrue(this.s1 instanceof Song);
        assertTrue(this.s1 instanceof Music);
        assertEquals("My Fault", this.s1.getName());
        assertEquals("Eminem", this.s1.getArtist());
        assertEquals("defaultSong.png", this.s1.getPicturePath());

        assertEquals("Neighbors", this.s2.getName());
        assertEquals("J. Cole", this.s2.getArtist());
        assertEquals("forYourEyesOnly.jpg", this.s2.getPicturePath());

        assertEquals("A.D.H.D", this.s3.getName());
        assertEquals("Kendrick Lamar", this.s3.getArtist());
        assertEquals("defaultSong.png", this.s3.getPicturePath());
    }

    @Test
    public void testSetName() {
        assertEquals("My Fault", this.s1.getName());
        this.s1.setName("Superman");
        assertEquals("Superman", this.s1.getName());

        assertThrows(IllegalArgumentException.class, () -> this.s1.setName(""));
        assertThrows(IllegalArgumentException.class, () -> this.s1.setName("a;a"));
        assertThrows(IllegalArgumentException.class, () -> this.s1.setName("a,a"));
        assertThrows(NullPointerException.class, () -> this.s1.setName(null));
    }

    @Test
    public void testSetPicturePath() {
        assertEquals("defaultSong.png", this.s1.getPicturePath());
        this.s1.setPicturePath("forYourEyesOnly.jpg");
        assertEquals("forYourEyesOnly.jpg", this.s1.getPicturePath());

        this.s1.setPicturePath("forYourEyezOnly.jpg");
        assertEquals("defaultSong.png", this.s1.getPicturePath());
    }

    @Test
    public void testSetArtist() {
        assertEquals("Eminem", this.s1.getArtist());
        this.s1.setArtist("Kendrick Lamar");
        assertEquals("Kendrick Lamar", this.s1.getArtist());

        assertThrows(IllegalArgumentException.class, () -> this.s1.setArtist(""));
        assertThrows(IllegalArgumentException.class, () -> this.s1.setArtist("a;a"));
        assertThrows(IllegalArgumentException.class, () -> this.s1.setArtist("a,a"));
        assertThrows(NullPointerException.class, () -> this.s1.setArtist(null));
    }

    @Test
    public void testSetAlbum() {
        assertEquals("The Eminem Show", this.s1.getAlbum());
        this.s1.setAlbum("The Marshall Mather LP");
        assertEquals("The Marshall Mather LP", this.s1.getAlbum());

        assertThrows(IllegalArgumentException.class, () -> this.s1.setAlbum(""));
        assertThrows(IllegalArgumentException.class, () -> this.s1.setAlbum("a;a"));
        assertThrows(IllegalArgumentException.class, () -> this.s1.setAlbum("a,a"));
        assertThrows(NullPointerException.class, () -> this.s1.setAlbum(null));
    }
}
