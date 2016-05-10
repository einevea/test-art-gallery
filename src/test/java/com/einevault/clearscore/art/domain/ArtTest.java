package com.einevault.clearscore.art.domain;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * Created by einevea on 08/05/2016.
 */
public class ArtTest {
    private final String name = "Mona Lisa";
    private final String artist = "Leonardo da Vinci";
    private final ArtType painting = ArtType.PAINTING;
    private final Date created = new Date();
    private final Optional<BigDecimal> askPrice = Optional.empty();


    @Test(expected = NullPointerException.class)
    public void testNullNameArt() {
        String name = null;
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNameArt() {
        String name = "";
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }

    @Test(expected = NullPointerException.class)
    public void testNullTypeArt() {
        ArtType painting = null;
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }

    @Test(expected = NullPointerException.class)
    public void testNullArtistArt() {
        String artist = null;
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArtistArt() {
        String artist = "";
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }

    @Test(expected = NullPointerException.class)
    public void testNullCreatedArt() {
        Date created = null;
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }

    @Test
    public void testNoAskPriceArt() {
        Art art = new Art(name, painting, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
        art = new Art(name, painting, askPrice, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }

    @Test
    public void testAskPrice1pieceArt() {
        Optional<BigDecimal> askPrice = Optional.of(BigDecimal.ONE);
        Art art = new Art(name, painting, askPrice, artist, created);
        AssertArt.equals(name, painting, artist, created, askPrice, art);
    }

    @Test
    public void testEqualForSimilarArt() {
        Art artV1 = new Art(name, painting, artist, created);
        Optional<BigDecimal> askPrice = Optional.of(BigDecimal.ONE);
        Art artV2 = new Art(name, painting, askPrice, artist, created);
        Assert.assertEquals(artV1, artV2);
    }

    @Test
    public void testNonEqualForUpperCaseArt() {
        Art artV1 = new Art(name, painting, artist, created);
        Art artV2 = new Art(name.toUpperCase(), painting, artist, created);
        Assert.assertNotEquals(artV1, artV2);
    }

    @Test
    public void testNonEqualKeyForUpperCaseArt() {
        Art artV1 = new Art(name, painting, artist, created);
        Art artV2 = new Art(name.toUpperCase(), painting, artist, created);
        Assert.assertNotEquals(artV1.getKey(), artV2.getKey());
    }

    @Test
    public void testEqualKeyForSimilarArt() {
        Art artV1 = new Art(name, painting, artist, created);
        Optional<BigDecimal> askPrice = Optional.of(BigDecimal.ONE);
        Art artV2 = new Art(name, painting, askPrice, artist, created);
        Assert.assertEquals(artV1.getKey(), artV2.getKey());
    }

}
