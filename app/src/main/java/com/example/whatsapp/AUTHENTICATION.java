package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.ActivityAUTHENTICATIONBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AUTHENTICATION extends AppCompatActivity {
ActivityAUTHENTICATIONBinding binding;
private FirebaseAuth auth;
FirebaseDatabase database;
ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAUTHENTICATIONBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
database=FirebaseDatabase.getInstance();
progressDialog=new ProgressDialog(AUTHENTICATION.this);

progressDialog.setTitle("Creating Account");
progressDialog.setMessage("We are creating your account");


binding.signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        progressDialog.show();


        auth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                progressDialog.dismiss();


                if(task.isSuccessful()){
Users user=new Users(binding.username.getText().toString(),binding.email.getText().toString(),binding.password.getText().toString());
String id=task.getResult().getUser().getUid();
database.getReference().child("Users").child(id).setValue(user);

                    Toast.makeText(AUTHENTICATION.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();

                }

                else{

                    Toast.makeText(AUTHENTICATION.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
});

        binding.alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AUTHENTICATION.this,SignInActivity.class);
                startActivity(intent);

            }
        });

    }
}