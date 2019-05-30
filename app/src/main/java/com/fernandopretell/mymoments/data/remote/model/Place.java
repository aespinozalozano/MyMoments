package com.fernandopretell.mymoments.data.remote.model;

import java.util.List;

public class Place {
    private String id;
    private String descriptionPlace;
    private List<String> arrayPictures;
    private long createdAt;

    public Place(String id, String descriptionPlace, List<String> arrayPictures, long createdAt) {
        this.id = id;
        this.descriptionPlace = descriptionPlace;
        this.arrayPictures = arrayPictures;
        this.createdAt = createdAt;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescriptionPlace() {
        return descriptionPlace;
    }

    public void setDescriptionPlace(String descriptionPlace) {
        this.descriptionPlace = descriptionPlace;
    }

    public List<String> getArrayPictures() {
        return arrayPictures;
    }

    public void setArrayPictures(List<String> arrayPictures) {
        this.arrayPictures = arrayPictures;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
