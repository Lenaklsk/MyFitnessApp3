package com.example.myfitnessapp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfitnessapp3.javaclasses.DAOModel;
import com.example.myfitnessapp3.javaclasses.Model;
import com.example.myfitnessapp3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ExcerciseAdapter extends RecyclerView.Adapter<ExcerciseAdapter.MyViewHolder> {

    Context context;

    ArrayList<Model> list;

    public ExcerciseAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.excercise, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = list.get(position);
        int size = list.size();
        holder.excerciseName.setText(model.getImageName());
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);

        holder.setIsRecyclable(false);

        holder.optionText.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.optionText);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item ->{
                switch(item.getItemId()){
                    case R.id.menu_edit:
                        //https://www.youtube.com/watch?v=68vp1O7Tukg ca. min 4:30
                       /* Intent intent = new Intent(context,Excercise.class);
                        intent.putExtra("EDIT", String.valueOf(model));
                        context.startActivity(intent);*/
                    break;
                    case R.id.menu_remove:
                        DAOModel daoModel = new DAOModel();
                        int listSize = list.size();
                        daoModel.remove(model.getKey(), model.getImageUrl()).addOnSuccessListener(suc ->
                        {
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(0, listSize);
                            list.subList(listSize, list.size()).clear();
                            list.remove(position);
                            notifyDataSetChanged();



                        }).addOnFailureListener(er ->
                        {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                           break;
               }
                return false;
            });
            popupMenu.show();
        });



        /*((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.colorAccent);
                CharSequence options[]=new CharSequence[]{
                        // select any from the value
                        "Delete",
                        "Edit",
                        "Cancel"
                };
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Delete or Edit Exercise: "+model.getImageName());
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // if delete option is choosed
                        // then call delete function
                        if(which==0) {
                           delete(position);
                        }
                        if(which==1){
                            //edit(position,time);
                        }
                        v.setBackgroundResource(R.color.white);

                    }
                });
                builder.show();
            }
        });*/

        //Glide.with(context).load("https://Exercise%2F1672330300580.jpge").into(holder.imageView);
    }

    private void delete(int position) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Exercise");

        //dbref.getRef(position).removeValue();
        dbref.setValue(null);


        //Query dbref= FirebaseDatabase.getInstance().getReference().child("Exercise").child("imageName");
        /*String key =  dbref.getKey();
        dbref= FirebaseDatabase.getInstance().getReference().child("Exercise").child("imageName").child(key);
        dbref.removeValue();*/
        // we are use add listerner
        // for event listener method
        // which is called with query.


        /*dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // remove the value at reference
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView excerciseName, optionText;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            excerciseName = itemView.findViewById(R.id.excerciseName);
            imageView = itemView.findViewById(R.id.excerciseImage);
            optionText = itemView.findViewById(R.id.option_text);

        }
    }
}
