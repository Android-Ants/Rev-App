/*
______________Documentation_________________

object ---
1:
Randomize obj = new Randomize(ArrayList<ModelImage> arr);
obj.randomize();                                         -- to randomize arr
ArrayList<ModelImage> arrRand = obj.getRandomized();     -- to get a randomized list of data of arr
ArrayList<ModelImage> arrRand = obj.getPrevRandomized(); -- to get previously randomized list

2:
Randomize obj = new Randomize();
ArrayList<ModelImage> arrRand = obj.getRandomized(ArrayList<ModelImage> data); -- to get randomized list data
ArrayList<ModelImage> arrRand = obj.getPrevRandomized();                       -- to get previously randomized

*/



package com.example.galleryapp;

import com.example.galleryapp.models.ModelImage;

import java.util.ArrayList;

public class Randomize {

    private static ArrayList<ModelImage> rawList = new ArrayList<>();
    private static ArrayList<ModelImage> randomizedList = new ArrayList<>();

    public  Randomize(){

    }

    public Randomize(ArrayList<ModelImage> rawList) {
        this.rawList = rawList;
    }

    public static ArrayList<ModelImage> getRandomized(){
        ArrayList<ModelImage> processingList = new ArrayList<>();
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

    public static ArrayList<ModelImage> getRandomized(ArrayList<ModelImage> input){
        ArrayList<ModelImage> processingList2 = new ArrayList<>();
        randomizedList.clear();
        processingList2 .addAll(input);
        while(processingList2.size()!=0)
        {
            int index = (int) (Math.random()*processingList2.size());
            randomizedList.add(processingList2.get(index));
            processingList2.remove(index);
        }
        return randomizedList;
    }

    public static void randomize(){
        ArrayList<ModelImage> newArr = new ArrayList<ModelImage>(rawList);
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

    public static ArrayList<ModelImage> getPrevRandomized(){
        return randomizedList;
    }

}
