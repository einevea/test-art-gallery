package com.einevault.clearscore.art.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by einevea on 09/05/2016.
 */
public final class GalleryFactory {

    private final Gallery defaultGallery = new GalleryImp(new ConcurrentHashMap<>());

    public Gallery getDefault() {
        return defaultGallery;
    }
}
