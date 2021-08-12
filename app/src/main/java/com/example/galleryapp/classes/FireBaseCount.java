package com.example.galleryapp.classes;

public class FireBaseCount {

    private String id;
    private String parentsId;
    private String url = "";
    private String name = "";
    private String liked = "false";
    private String count = "0";
    private String lastSeen = "";

    public FireBaseCount(String id, String url, String name, String liked, String count, String lastSeen) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.liked = liked;
        this.count = count;
        this.lastSeen = lastSeen;
    }

    public FireBaseCount(String url, String id) {
        this.id = id;
        this.url = url;
    }

    public FireBaseCount(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public FireBaseCount ()
    {

    }

    public String getParentsId() {
        return parentsId;
    }

    public void setParentsId(String parentsId) {
        this.parentsId = parentsId;
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

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }
}
