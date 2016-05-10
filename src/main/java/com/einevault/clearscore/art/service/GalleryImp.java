package com.einevault.clearscore.art.service;

import com.einevault.clearscore.art.domain.Art;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by einevea on 09/05/2016.
 */
public class GalleryImp implements Gallery {

    private final ConcurrentHashMap<UUID, Art> galleryDB;

    GalleryImp(ConcurrentHashMap<UUID, Art> galleryDB) {
        this.galleryDB = galleryDB;
        // Class only accessible through the factory outside the package
    }

    @Override
    public void addArt(Art art) {
        if (art != null) {
            galleryDB.put(art.getKey(), art);
        }
    }

    @Override
    public void deleteArt(UUID uuid) {
        galleryDB.remove(uuid);
    }

    @Override
    public Collection<Art> getAllArt() {
        return galleryDB.values().stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getArtists() {
        return galleryDB.values().stream()
                .map(Art::getArtist)
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .parallel()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Art> getArtByArtist(String artist) {
        return galleryDB.values().stream()
                .filter(art -> Objects.equals(art.getArtist(), artist))
                .parallel()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Art> getRecentArt() {
        Date lastYear = getLastYearDate();

        return galleryDB.values().stream()
                .filter(art -> lastYear.before(art.getCreated()))
                .parallel()
                .collect(Collectors.toList());
    }

    private Date getLastYearDate() {
        LocalDateTime lastYearDateTime = LocalDateTime.now().with(temporal -> temporal.minus(1, ChronoUnit.YEARS));
        return Date.from(lastYearDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Collection<Art> getArtByPrice(Optional<BigDecimal> lowerLimit, Optional<BigDecimal> upperLimit) {

        Stream<Art> byPrice = galleryDB.values().stream()
                .filter(art -> art.getAskingPrice() != null);

        if (lowerLimit.isPresent()) {
            byPrice = byPrice.filter(art -> art.getAskingPrice().isPresent())
                    .filter(art -> art.getAskingPrice().get().compareTo(lowerLimit.get()) >= 0);
        }

        if (upperLimit.isPresent()) {
            byPrice = byPrice.filter(art -> art.getAskingPrice().isPresent())
                    .filter(art -> art.getAskingPrice().get().compareTo(upperLimit.get()) <= 0);
        }

        return byPrice.parallel().collect(Collectors.toList());
    }


}
