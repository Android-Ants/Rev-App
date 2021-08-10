package com.example.galleryapp.models;

public class ModelImage {

    private String id;

    private String name;

    private String Url;


    private int count = 0;

    private String last_seen = "";

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
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


    public ModelImage(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModelImage(String id, String name, String url, int count) {
        this.id = id;
        this.name = name;
        Url = url;
        this.count = count;
    }

    public ModelImage(String Url){
        this.Url = Url;
    }
}
