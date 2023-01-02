package com.example.myfitnessapp3.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myfitnessapp3.activities.CreateUebungActivity;
import com.example.myfitnessapp3.adapter.ExcerciseAdapter;
import com.example.myfitnessapp3.javaclasses.Model;
import com.example.myfitnessapp3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Excercise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Excercise extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database;
    ExcerciseAdapter adapter;
    ArrayList<Model> list;
    Button addExercise;
    ImageView deleteExercise;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Excercise() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Excercise.
     */
    // TODO: Rename and change types and number of parameters
    public static Excercise newInstance(String param1, String param2) {
        Excercise fragment = new Excercise();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_excercise, container, false);
       ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_excercise,null);

        recyclerView = root.findViewById(R.id.excercise_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = FirebaseDatabase.getInstance("https://auth-3df76-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        DatabaseReference db = database.child("Exercise");

        list = new ArrayList<>();

        adapter = new ExcerciseAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    model.setKey(dataSnapshot.getKey());
                    list.add(model);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addExercise = root.findViewById(R.id.add_exercise);
        addExercise.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), CreateUebungActivity.class);
                startActivity(intent);
            }
        });




        // Inflate the layout for this fragment
        return root;


    }

    public void onStart() {
        super.onStart();


    }
    public void onResume() {
        super.onResume();
        adapter.notifyItemRangeChanged(0, list.size());
    }


}