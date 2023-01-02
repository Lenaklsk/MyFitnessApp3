package com.example.myfitnessapp3.ui.exercise;

import android.content.Intent;
import android.graphics.Movie;
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
import android.widget.Toast;

import com.example.myfitnessapp3.databinding.ExcerciseBinding;
import com.example.myfitnessapp3.fertigeKlassen.MainActivity;
import com.example.myfitnessapp3.ui.base.BaseFragment;
import com.example.myfitnessapp3.ui.view.activities.CreateUebungActivity;
import com.example.myfitnessapp3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Excercise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Excercise extends BaseFragment <ExcerciseBinding, ExerciseViewModel> implements ExcerciseAdapter.ExerciseListener{

    ExcerciseAdapter exerciseAdapter;
    RecyclerView recyclerView;
    Button newButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Excercise() {
        // Required empty public constructor
    }
    public static Excercise newInstance(String param1, String param2) {
        Excercise fragment = new Excercise();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected ExerciseViewModel createViewModel() {
        return null;
    }

    @NonNull
    @Override
    protected ExcerciseBinding createViewBinding(LayoutInflater layoutInflater) {
        return ExerciseBinding.inflate(layoutInflater);
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

       //hier problem mit listener
       exerciseAdapter = new ExcerciseAdapter(this);
       recyclerView.setAdapter(exerciseAdapter);

       setListeners(root);
      // ovserveViewModel();


        return root;
    }

    private void setListeners(ViewGroup root) {
        newButton = root.findViewById(R.id.add_exercise);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateUebungActivity.class);
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
       /* viewModel.getShowLoadingLiveData().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getHideLoadingLiveData().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getMoviesLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setItems(movies);
            }
        });

        viewModel.getNavigateToDetailsLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                DetailsActivity.start(MainActivity.this, movie);
            }
        });

        viewModel.getShowErrorMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onExerciseClicked(ExerciseModel exercise) {
        //hier eventuell onThreedotsClicked macht was, nicht der Klick auf Exercise
        viewModel.onExerciseClicked(exercise);
    }
}