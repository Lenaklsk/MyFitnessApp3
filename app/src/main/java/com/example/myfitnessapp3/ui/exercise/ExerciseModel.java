package com.example.myfitnessapp3.ui.exercise;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class ExerciseModel implements Serializable {

    String imageName, imageUrl;
    @Exclude
    String key;

    public ExerciseModel(){

    }

    public ExerciseModel(String imageName, String imageUrl){ //String downloadUrl){
        this.imageName = imageName;
        this.imageUrl = imageUrl;

    }

    public String getImageName(){
        return imageName;
    }
    public String getImageUrl(){
        return imageUrl;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
