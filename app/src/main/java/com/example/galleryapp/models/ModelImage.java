package com.example.galleryapp.models;

public class ModelImage {

    private String id;
    private String name;
    private String Url;
    private String count = "0";
    private String last_seen = "";
    private String liked = "false";



    public String isLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelImage() {
    }

    public ModelImage(String count, String id, String liked, String name, String url) {
        this.id = id;
        this.name = name;
        this.Url = url;
        this.count = count;
        this.liked = liked;
    }

    public ModelImage(String id, String name, String url, String count, String last_seen, String liked) {
        this.id = id;
        this.name = name;
        this.Url = url;
        this.count = count;
        this.last_seen = last_seen;
        this.liked = liked;
    }


    public ModelImage(String Url){
        this.Url = Url;
    }
}
