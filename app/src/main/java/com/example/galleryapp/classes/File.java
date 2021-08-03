package com.example.galleryapp.classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class File implements Serializable {

    @SerializedName("kind")
    private String kind;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("mimeType")
    private String mimeType;

    public File ()
    {

    }

    public File(String kind, String id, String name, String mimeType) {
        this.kind = kind;
        this.id = id;
        this.name = name;
        this.mimeType = mimeType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
