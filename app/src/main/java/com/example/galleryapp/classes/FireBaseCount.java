package com.example.galleryapp.classes;

public class FireBaseCount {

    private String id;
    private String url = "";
    private String name = "";
    private int count = 0 ;

    public FireBaseCount(String id, String url, int count) {
        this.id = id;
        this.url = url;
        this.count = count;
    }

    public FireBaseCount(String id, String url, String name, int count) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.count = count;
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
}
