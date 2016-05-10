package com.einevault.clearscore.art.service;

import com.einevault.clearscore.art.domain.Art;
import com.einevault.clearscore.art.domain.ArtType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by einevea on 09/05/2016.
 */
public class GalleryTest {

    private GalleryFactory factory = new GalleryFactory();
    private Gallery mockedGallery;
    private Gallery gallery;
    private ConcurrentHashMap<UUID, Art> galleryDB;

    private Art art = new Art("David", ArtType.SCULPTURE, "Michelangelo", new Date());
    private Art art_repeated = new Art("David", ArtType.SCULPTURE, Optional.of(BigDecimal.TEN), "Michelangelo", new Date());
    private Art art2 = new Art("Bayeux Tapestry", ArtType.TAPESTRY, Optional.of(BigDecimal.ONE), "Unknown", new Date());
    private Art art3 = new Art("Bilingual vase painting", ArtType.VASE, Optional.of(BigDecimal.TEN), "Unknown", new Date(1));

    @Before
    public void setUpTest() {
        galleryDB = Mockito.mock(ConcurrentHashMap.class);
        mockedGallery = new GalleryImp(galleryDB);

        gallery = factory.getDefault();
        gallery.addArt(art);
        gallery.addArt(art2);
        gallery.addArt(art3);
        gallery.addArt(art_repeated);
    }

    @After
    public void tearDownTest() {
        galleryDB = null;
        gallery = null;
        mockedGallery = null;
    }

    @Test
    public void testAddDiferentArt() {
        mockedGallery.addArt(art);
        mockedGallery.addArt(art2);

        verify(galleryDB, times(1)).put(art.getKey(), art);
        verify(galleryDB, times(1)).put(art2.getKey(), art2);
    }

    @Test
    public void testAddRepeatedArt() {
        mockedGallery.addArt(art);
        mockedGallery.addArt(art_repeated);

        verify(galleryDB, times(2)).put(art.getKey(), art);
    }

    @Test
    public void testDeleteArt() {
        mockedGallery.deleteArt(art.getKey());

        verify(galleryDB, times(1)).remove(art.getKey());
    }

    @Test
    public void testGetAllArt() {
        List<Art> allArtOrigin = Arrays.asList(art_repeated, art2, art3);

        Collection<Art> allArt = gallery.getAllArt();
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    @Test
    public void testGetArtists() {
        List<String> originArtist = Arrays.asList(art.getArtist(), art2.getArtist());

        List<String> allArtist = gallery.getArtists();

        assertNotNull(allArtist);
        assertEquals(2, allArtist.size());
        assertEquals(originArtist, allArtist);
    }

    @Test
    public void testGetArtByArtist() {
        List<Art> allArtOrigin = Arrays.asList(art2, art3);

        Collection<Art> allArt = gallery.getArtByArtist(art2.getArtist());
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    @Test
    public void testGetRecentArt() {
        List<Art> allArtOrigin = Arrays.asList(art_repeated, art2);

        Collection<Art> allArt = gallery.getRecentArt();
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    @Test
    public void testGetArtByMinPrice() {
        List<Art> allArtOrigin = Arrays.asList(art_repeated, art2, art3);

        Collection<Art> allArt = gallery.getArtByPrice(Optional.of(BigDecimal.ONE), Optional.empty());
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    @Test
    public void testGetArtByMaxPrice() {
        List<Art> allArtOrigin = Arrays.asList(art2);

        Collection<Art> allArt = gallery.getArtByPrice(Optional.empty(), Optional.of(BigDecimal.ONE));
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    @Test
    public void testGetArtByMinMaxPrice() {
        List<Art> allArtOrigin = Arrays.asList(art_repeated, art3);

        Collection<Art> allArt = gallery.getArtByPrice(Optional.of(new BigDecimal(3)), Optional.of(new BigDecimal(11)));
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    @Test
    public void testGetNonArtByMinMaxPrice() {
        List<Art> allArtOrigin = Arrays.asList();
        Collection<Art> allArt = gallery.getArtByPrice(Optional.of(new BigDecimal(3)), Optional.of(new BigDecimal(6)));
        assertCollectionAgainstList(allArt, allArtOrigin);
    }

    private void assertCollectionAgainstList(Collection<Art> allArt, List<Art> allArtOrigin) {
        assertNotNull(allArt);
        assertEquals(allArtOrigin.size(), allArt.size());
        String msg = "The collection: " + allArt.toString().replace("Art{", "\n  Art{") + "\ndoesn't contain all elements in list: " + allArtOrigin.toString().replace("Art{", "\n  Art{");
        assertTrue(msg, allArt.containsAll(allArtOrigin));
    }
}
