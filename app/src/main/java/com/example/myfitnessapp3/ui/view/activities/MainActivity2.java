package com.example.myfitnessapp3.ui.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfitnessapp3.ui.exercise.ExerciseModel;
import com.example.myfitnessapp3.R;
import com.example.myfitnessapp3.ui.exercise.ExcerciseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ExcerciseAdapter adapter;
    ArrayList<ExerciseModel> list;
    Button createUebung;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.excercise_recycler2);
        database = FirebaseDatabase.getInstance("https://auth-3df76-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        //neu
        DatabaseReference root = database.child("Exercise");
        //neu
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ExcerciseAdapter(this, list);
        recyclerView.setAdapter(adapter);
       // database.addValueEventListener(new ValueEventListener() {
        //neu
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ExerciseModel model = dataSnapshot.getValue(ExerciseModel.class);
                    list.add(model);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    createUebung = findViewById(R.id.button);
       createUebung.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent intent = new Intent(MainActivity2.this, CreateUebungActivity.class);
            startActivity(intent);
        }
    });

    }
}