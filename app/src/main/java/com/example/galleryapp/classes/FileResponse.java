package com.example.galleryapp.classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FileResponse implements Serializable {

    @SerializedName("kind")
    private String kind;
    @SerializedName("nextPageToken")
    private String nextPageToken;
    @SerializedName("incompleteSearch")
    private Boolean incompleteSearch;
    @SerializedName("files")
    private List<File> fileList;

    public FileResponse ()
    {

    }

    public FileResponse(String kind, String nextPageToken, Boolean incompleteSearch, List<File> fileList) {
        this.kind = kind;
        this.nextPageToken = nextPageToken;
        this.incompleteSearch = incompleteSearch;
        this.fileList = fileList;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public Boolean getIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(Boolean incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
