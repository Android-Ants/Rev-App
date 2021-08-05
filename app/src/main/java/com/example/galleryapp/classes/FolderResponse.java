package com.example.galleryapp.classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FolderResponse implements Serializable {

    @SerializedName("kind")
    private String kind;
    @SerializedName("nextPageToken")
    private String nextPageToken;
    @SerializedName("incompleteSearch")
    private Boolean incompleteSearch;
    @SerializedName("files")
    private List<Folder> folderList;

    public FolderResponse()
    {

    }

    public FolderResponse(String kind, String nextPageToken, Boolean incompleteSearch, List<Folder> folderList) {
        this.kind = kind;
        this.nextPageToken = nextPageToken;
        this.incompleteSearch = incompleteSearch;
        this.folderList = folderList;
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

    public List<Folder> getFileList() {
        return folderList;
    }

    public void setFileList(List<Folder> folderList) {
        this.folderList = folderList;
    }
}
