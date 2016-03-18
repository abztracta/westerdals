package no.wact.pg3100.assignment3.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static test.helper.TestTableHelper.createTableAndFixtures;
import static test.helper.TestTableHelper.removeTestTable;

public class MusicHandlerTest {

    private MusicHandler handler;
    
    @Before
    public void setUp() throws Exception {
        createTableAndFixtures();
        handler = new MusicHandler();
    }

    @After
    public void tearDown() throws Exception {
        removeTestTable();
    }

    @Test
    public void testGetAlbums() throws Exception {
        List<Album> albumList = handler.getAlbums("Rock");
        assertEquals(3, albumList.size());
        assertAlbum("Bruce Springsteen", "Darkness on the Edge of Town", 5, 1978, "Rock", albumList.get(0));
        assertAlbum("Chris Spedding", "Hurt", 8, 1977, "Rock", albumList.get(1));
        assertAlbum("Deep Purple", "In Rock", 8, 1970, "Rock", albumList.get(2));
    }

    private void assertAlbum(String artist, String title, int tracks, int released, String genre, Album album) {
        assertNotNull(album);
        assertEquals(artist, album.getArtist());
        assertEquals(title, album.getTitle());
        assertEquals(tracks, album.getTracks());
        assertEquals(released, album.getReleasedYear());
        assertEquals(genre, album.getGenre());
    }

    @Test
    public void testGetAlbumsNullOrEmptyString() throws Exception {
        List<Album> albumList = handler.getAlbums("");
        assertEquals(0, albumList.size());
        albumList = handler.getAlbums(null);
        assertEquals(0, albumList.size());
        albumList = handler.getAlbums("Agenrethatdoesnotexist");
        assertEquals(0, albumList.size());
    }
}