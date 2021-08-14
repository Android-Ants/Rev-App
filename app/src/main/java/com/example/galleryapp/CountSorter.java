package com.example.galleryapp;

import com.example.galleryapp.classes.FireBaseCount;

import java.util.Comparator;

public class CountSorter implements Comparator<FireBaseCount> {
    @Override
    public int compare(FireBaseCount o1, FireBaseCount o2) {
        return o2.getCount().compareTo(o1.getCount());
    }
}
