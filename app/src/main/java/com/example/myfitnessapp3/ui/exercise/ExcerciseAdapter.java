package com.example.myfitnessapp3.ui.exercise;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfitnessapp3.javaclasses.DAOModel;
import com.example.myfitnessapp3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExcerciseAdapter extends RecyclerView.Adapter<ExcerciseAdapter.ExerciseViewHolder> {

    public interface ExerciseListener{
        void onExerciseClicked(ExerciseModel exercise);
    }

    Context context;
    ArrayList<ExerciseModel> items;
    private final ExerciseListener listener;

    public ExcerciseAdapter(ExerciseListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    public void setItems(ArrayList<ExerciseModel> items){
        this.items = items;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //eigentlich mit binding??
        View v = LayoutInflater.from(context).inflate(R.layout.excercise, parent, false);
        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        //holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private ExerciseModel getItem(int position) {
        return items.get(position);
    }


   public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       public ExerciseViewHolder(@NonNull View itemView) {
           super(itemView);
       }

       @Override
       public void onClick(View view) {

       }
   }

       /* ItemExerciseBinding binding;
       ExerciseViewHolder(ItemExerciseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
           ExerciseModel exercise = getItem(position);

            setClickListener(exercise);
            setTitle(exercise.getImageName());
            setImage(exercise.getImageUrl());
        }

        /private void setTitle(String title) {
            binding.title.setText(title);
        }

        private void setImage(String imageUrl) {
            Glide.with(itemView.getContext()).load(imageUrl).into(binding.image);
        }

        private void setDescription(String description) {
            binding.desc.setText(description);
        }

        private void setClickListener(ExerciseModel exercise) {
            itemView.setTag(exercise);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onExerciseClicked((ExerciseModel) view.getTag());
        }
    }*/




}
