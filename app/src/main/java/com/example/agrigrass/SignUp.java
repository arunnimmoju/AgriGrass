package com.example.agrigrass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agrigrass.model.User;
import com.example.agrigrass.util.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText username, email, password;
    Button signupBtn;
    Button loginBtn;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signupBtn = findViewById(R.id.btnSignUp);
        loginBtn = findViewById(R.id.btnLogin);


        mfirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uemail = email.getText().toString();
                final String uname = username.getText().toString();
                final String upwd = password.getText().toString();

                if (uname.isEmpty()) {
                    username.setError("Please Provide user name");
                    email.requestFocus();
                } else if (uemail.isEmpty()) {
                    email.setError("Please Provide Email");
                    email.requestFocus();
                } else if (upwd.isEmpty()) {
                    password.setError("Please Provide Password");
                    email.requestFocus();
                } else if (!(uemail.isEmpty() && upwd.isEmpty())) {
                    mfirebaseAuth.createUserWithEmailAndPassword(uemail, upwd)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        CustomToast.createToast(SignUp.this,
                                                "Signup Unsuccessfull..! TRY AGAIN" + task.getException().getMessage(), true);
                                    } else {
                                        User user = new User(uname);
                                        String uid = task.getResult().getUser().getUid();

                                        firebaseDatabase.getReference(uid).setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent intent = new Intent(SignUp.this, Login.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        intent.putExtra("name", uname);
                                                        startActivity(intent);
                                                        Toast.makeText(SignUp.this, "SignUp successful Now Login", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                }
                            });


                } else {
                    CustomToast.createToast(SignUp.this, "Error Occured", true);

                }


            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
}