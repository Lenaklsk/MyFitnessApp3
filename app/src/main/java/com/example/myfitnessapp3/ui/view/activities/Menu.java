package com.example.myfitnessapp3.ui.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myfitnessapp3.R;
import com.example.myfitnessapp3.databinding.ActivityMenuBinding;
import com.example.myfitnessapp3.ui.exercise.Excercise;
import com.example.myfitnessapp3.ui.category.Kategorien;
import com.example.myfitnessapp3.ui.workout.Workouts;


public class Menu extends AppCompatActivity {
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //etContentView(R.layout.activity_test);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Excercise());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.excerciseIcon:
                    replaceFragment(new Excercise());
                    break;
                case R.id.categoryIcon:
                    replaceFragment(new Kategorien());
                    break;
                case R.id.workoutIcon:
                    replaceFragment(new Workouts());
                    break;
            }
            return true;
        });



    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}