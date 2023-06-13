package com.ethiopia.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText email ,pass;
    Button create,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);

        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);

        create=findViewById(R.id.create);
        login=findViewById(R.id.login);

            firebaseAuth = FirebaseAuth.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Toast.makeText(this, " You Are register to firebase ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, " Please register to firebase ", Toast.LENGTH_LONG).show();
        }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String text1 =email.getText().toString();
            String text2 =pass.getText().toString();

            if (text1.isEmpty()||text2.isEmpty()){
                email.setError(" Please fill the Email ");
                pass.setError(" Please fill the password ");
            }else {

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Authentication.this, " The Registration is Success ", Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(Authentication.this, MainActivity.class));

                        } else {
                            Toast.makeText(Authentication.this, " The Registration is filed ", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Authentication.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String text1 =email.getText().toString();
                String text2 =pass.getText().toString();

                if (text1.isEmpty()||text2.isEmpty()){
                    email.setError(" Please fill the Email ");
                    pass.setError(" Please fill the password ");
                }else {

                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                                    pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Authentication.this, "Login Successfully ", Toast.LENGTH_LONG).show();
                                    //    startActivity(new Intent(Authentication.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(Authentication.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }
                }
        });

    }
}