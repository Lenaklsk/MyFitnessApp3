package com.example.myfitnessapp3.fertigeKlassen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitnessapp3.R;
import com.example.myfitnessapp3.ui.view.activities.Menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register_text);

        //register.setOnClickListener(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.register_text:
                        startActivity(new Intent(MainActivity.this, RegisterUser.class));
                        break;
                    case R.id.forgotPassword_text:

                }
            }
        });

        signIn = (Button) findViewById(R.id.signIn_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        editTextEmail = (EditText) findViewById(R.id.email_editText);
        editTextPassword = (EditText) findViewById(R.id.password_editText);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }
    /*public void onClick(View view){
        switch(view.getId()){
            case R.id.registerUser_button:
                startActivity(new Intent(MainActivity.this, RegisterUser.class));
                break;
            case R.id.signIn_button:
                userLogin();
                break;

        }

    }*/
    private void userLogin(){
        //take values of input and toString to add Validations
        //.trim() -> might extra space at the end
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Validations
        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        //is a valid email?? @ etc.
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Password too short");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            public void onComplete(Task< AuthResult > task){
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){

                        //redirect to home
                        startActivity(new Intent(MainActivity.this, Menu.class));
                    } else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}