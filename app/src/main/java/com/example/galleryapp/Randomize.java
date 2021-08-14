package com.example.galleryapp;

import com.example.galleryapp.classes.FireBaseCount;

import java.util.ArrayList;

public class Randomize {

    private static ArrayList<FireBaseCount> rawList = new ArrayList<>();
    private static ArrayList<FireBaseCount> randomizedList = new ArrayList<>();

    public  Randomize(){

    }

    public Randomize(ArrayList<FireBaseCount> rawList) {
        this.rawList = rawList;
    }

    public static ArrayList<FireBaseCount> getRandomized(){
        ArrayList<FireBaseCount> processingList = new ArrayList<>();
        randomizedList.clear();
        processingList.addAll(rawList);
        while(processingList.size()!=0)
        {
            int index = (int) (Math.random()*processingList.size());
            randomizedList.add(processingList.get(index));
            processingList.remove(index);
        }
        return randomizedList;
    }

    public static ArrayList<FireBaseCount> getRandomized(ArrayList<FireBaseCount> input){
        ArrayList<FireBaseCount> processingList2 = new ArrayList<>(input);
        randomizedList.clear();
        while(processingList2.size()!=0)
        {
            int index = (int) (Math.random()*processingList2.size());
            randomizedList.add(processingList2.get(index));
            processingList2.remove(index);
        }
        return randomizedList;
    }

    public static void randomize(){
        ArrayList<FireBaseCount> newArr = new ArrayList<FireBaseCount>(rawList);
        rawList.clear();
        randomizedList.clear();
        while(newArr.size()!=0)
        {
            int index = (int) (Math.random()*newArr.size());
            rawList.add(newArr.get(index));
            randomizedList.add(newArr.get(index));
            newArr.remove(index);
        }
    }

    public static ArrayList<FireBaseCount> getPrevRandomized(){
        return randomizedList;
    }

}
