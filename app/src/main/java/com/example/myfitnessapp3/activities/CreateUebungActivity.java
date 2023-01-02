package com.example.myfitnessapp3.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.example.myfitnessapp3.R;
import com.example.myfitnessapp3.fertigeKlassen.MainActivity;
import com.example.myfitnessapp3.javaclasses.Model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreateUebungActivity extends AppCompatActivity {

    Uri imageUri;
    ImageView imageView;
    Button addButton;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    int Request_Code = 10;
    Uri PathUri;
    EditText exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_uebung);


        imageView = (ImageView) findViewById(R.id.choose_Image);
        exerciseName = (EditText) findViewById(R.id.uebung_editText);
        addButton = (Button) findViewById(R.id.addUebung_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");

        storageReference = FirebaseStorage.getInstance().getReference("Exercise");

        // gets root of the firebase json tree
        databaseReference = FirebaseDatabase.getInstance("https://auth-3df76-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Exercise");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Upload();
            }
        });
    }

     private void selectImage(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select image"),Request_Code);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==Request_Code && resultCode == RESULT_OK && data != null && data.getData()!=null){
            PathUri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), PathUri);
                imageView.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void Upload(){
        if(PathUri != null){
            progressDialog.show();
            StorageReference storageReference1 = storageReference.child(System.currentTimeMillis()+"."+GetFileExtension(PathUri));
            UploadTask uploadTask = storageReference1.putFile(PathUri);
            uploadTask
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           String ImageName = exerciseName.getText().toString().trim();
                           exerciseName.getText().clear();

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Toast.makeText(CreateUebungActivity.this, "Successfull uploaded", Toast.LENGTH_SHORT).show();

                           // Model model = new Model(ImageName, taskSnapshot.getUploadSessionUri().toString());
                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    Model model = new Model(ImageName,url);

                                    String ImageUploadId = databaseReference.push().getKey();
                                    //c.child is condition underneath the roots. Condition: value

                                    databaseReference.child(ImageUploadId).setValue(model);
                                    startActivity(new Intent(CreateUebungActivity.this, Menu.class));
                                }
                            });
                        }
                    });
        }

        }

}