package com.einevault.clearscore.art.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by einevea on 08/05/2016.
 */
public final class Art implements Entity {

    // Using UUID as a key for the art
    // though is possible that there is a collision its very unlikely
    private final UUID uuid;
    private final String name;
    private final ArtType type;
    private final Optional<BigDecimal> askingPrice;
    private final String artist;
    private final Date created;


    public Art(String name, ArtType type, String artist, Date created) {
        this(name, type, Optional.empty(), artist, created);
    }

    public Art(String name, ArtType type, Optional<BigDecimal> askingPrice, String artist, Date created) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        if (type == null) {
            throw new NullPointerException("type cannot be null");
        }

        if (artist == null) {
            throw new NullPointerException("artist cannot be null");
        } else if (artist.isEmpty()) {
            throw new IllegalArgumentException("artist cannot be empty");
        }

        if (created == null) {
            throw new NullPointerException("created cannot be null");
        }

        this.name = name;
        this.type = type;
        this.askingPrice = askingPrice;
        this.artist = artist;
        this.created = created;

        // Adding uuid as a function of the name, type and artist
        this.uuid = UUID.nameUUIDFromBytes((getName() + getType() + getArtist()).getBytes());
    }

    public Optional<BigDecimal> getAskingPrice() {
        return askingPrice;
    }

    public String getName() {
        return name;
    }

    public ArtType getType() {
        return type;
    }

    public String getArtist() {
        return artist;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Art{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", askingPrice=" + askingPrice +
                ", artist='" + artist + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Art art = (Art) o;

        if (!getName().equals(art.getName())) return false;
        if (getType() != art.getType()) return false;
        return getArtist().equals(art.getArtist());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getArtist().hashCode();
        return result;
    }

    @Override
    public UUID getKey() {
        return uuid;
    }
}
