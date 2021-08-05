package com.example.galleryapp.models;

public class ModelImage {

    private String id;

    private String name;

    private String filepath;

    private String Url;

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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public ModelImage(String id, String name, String filepath) {
        this.id = id;
        this.name = name;
        this.filepath = filepath;
    }

    public ModelImage(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModelImage(String Url){
        this.Url = Url;
    }
}
