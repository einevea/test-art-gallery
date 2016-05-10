package com.einevault.clearscore.art.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by einevea on 08/05/2016.
 */
public final class AssertArt {
    private AssertArt() {
        //Utility class
    }

    public static void equals(String expectedName, ArtType expectedArtType, String expectedAuthor, Date expectedCreated, Optional<BigDecimal> expectedAskingPrice, Art art) {
        assertEquals(expectedName, art.getName());
        assertEquals(expectedAuthor, art.getArtist());
        assertEquals(expectedArtType, art.getType());
        assertEquals(expectedCreated, art.getCreated());
        assertEquals(expectedAskingPrice, art.getAskingPrice());
    }
}
