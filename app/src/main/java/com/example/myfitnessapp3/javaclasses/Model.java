package com.example.myfitnessapp3.javaclasses;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class Model implements Serializable {

    String imageName, imageUrl;
    @Exclude
    String key;

    public Model(){

    }

    public Model(String imageName, String imageUrl){ //String downloadUrl){
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
