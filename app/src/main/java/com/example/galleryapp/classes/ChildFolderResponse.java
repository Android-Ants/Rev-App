package com.example.galleryapp.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChildFolderResponse {

    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;
    @SerializedName("selfLink")
    private Boolean selfLink;
    @SerializedName("items")
    private List<ChildFolder> items;

    public ChildFolderResponse ()
    {

    }

    public ChildFolderResponse(String kind, String etag, Boolean selfLink, List<ChildFolder> items) {
        this.kind = kind;
        this.etag = etag;
        this.selfLink = selfLink;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Boolean getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Boolean selfLink) {
        this.selfLink = selfLink;
    }

    public List<ChildFolder> getItems() {
        return items;
    }

    public void setItems(List<ChildFolder> items) {
        this.items = items;
    }
}
