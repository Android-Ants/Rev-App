package com.example.galleryapp.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParentFireBase implements Serializable {

    private String parentId ;
    private List<FireBaseCount> childs = new ArrayList<>();
    private Boolean blocked = false;
    private String Name  = "";

    public ParentFireBase ()
    {

    }

    public void  add_to_list(FireBaseCount fireBaseCount)
    {
        this.childs.add(fireBaseCount);
    }

    public ParentFireBase(String parentId, List<FireBaseCount> childs, Boolean blocked, String name) {
        this.parentId = parentId;
        this.childs = childs;
        this.blocked = blocked;
        Name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<FireBaseCount> getChilds() {
        return childs;
    }

    public void setChilds(List<FireBaseCount> childs) {
        this.childs = childs;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
