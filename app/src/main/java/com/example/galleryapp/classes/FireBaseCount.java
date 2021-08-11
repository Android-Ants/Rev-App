package com.example.galleryapp.classes;

public class FireBaseCount {

    private String id;
    private String url = "";
    private String name = "";
    private String last_seen = "";
    private int count = 0 ;
    private String liked = "false";

    public FireBaseCount(String url, String id, int count, String liked) {
        this.id = id;
        this.url = url;
        this.count = count;
        this.liked = liked;
    }

    public FireBaseCount(String id, String url, String name, int count, String liked) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.count = count;
        this.liked = liked;
    }

    public FireBaseCount ()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String isLiked() { return liked; }

    public void setLiked(String liked) { this.liked = liked; }
}
