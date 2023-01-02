package com.example.myfitnessapp3.javaclasses;

import com.example.myfitnessapp3.ui.exercise.ExerciseModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class DAOModel {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    public DAOModel()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance("https://auth-3df76-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = db.getReference("Exercise");
        storageReference = FirebaseStorage.getInstance().getReference("Exercise");
    }

    public Task<Void> add(ExerciseModel model)
    {
        return databaseReference.push().setValue(model);
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key, String imgUrl)
    {
        storageReference = storageReference.child(imgUrl);
        storageReference.delete();
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key)
    {
        if(key == null)
        {
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    public Query get()
    {
        return databaseReference;
    }
}
