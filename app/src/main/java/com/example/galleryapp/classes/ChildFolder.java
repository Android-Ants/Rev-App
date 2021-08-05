package com.example.galleryapp.classes;

import com.google.gson.annotations.SerializedName;

public class ChildFolder {

    @SerializedName("kind")
    private String kind;
    @SerializedName("id")
    private String id;
    @SerializedName("selfLink")
    private String selfLink;
    @SerializedName("childLink")
    private String childLink;

    public ChildFolder ()
    {

    }

    public ChildFolder(String kind, String id, String selfLink, String childLink) {
        this.kind = kind;
        this.id = id;
        this.selfLink = selfLink;
        this.childLink = childLink;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getChildLink() {
        return childLink;
    }

    public void setChildLink(String childLink) {
        this.childLink = childLink;
    }
}
