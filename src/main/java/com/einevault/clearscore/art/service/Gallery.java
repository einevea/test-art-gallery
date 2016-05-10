package com.einevault.clearscore.art.service;

import com.einevault.clearscore.art.domain.Art;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by einevea on 09/05/2016.
 */
public interface Gallery {

    /**
     * Add a piece of art to the gallery, replacing any existing equivalent piece;
     */
    void addArt(Art art);

    /**
     * Remove a specific piece of art from the gallery;
     */
    void deleteArt(UUID artKey);

    /**
     * Returns all art currently in the gallery
     */
    Collection<Art> getAllArt();

    /**
     * Returns the names of all artists with art currently in the gallery, in alphabetic order
     */
    List<String> getArtists();

    /**
     * Returns all art by a specific artist
     */
    Collection<Art> getArtByArtist(String artist);

    /**
     * Returns all art with a creation date in the past year
     */
    Collection<Art> getRecentArt();

    /**
     * Returns all art between an upper and a lower price limit. Both limits should be optional. Ignore art with no asking price
     */
    Collection<Art> getArtByPrice(Optional<BigDecimal> lowerLimit, Optional<BigDecimal> upperLimmit);
}
