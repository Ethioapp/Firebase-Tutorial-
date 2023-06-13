package com.ethiopia.firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button setdata,readdata,readdata2,auth,upload;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String number;
    int size;
    ArrayList list,list1,list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list =new ArrayList<>();
        list1 =new ArrayList<>();
        list2 =new ArrayList<>();


        setdata = findViewById(R.id.ms);
        auth = findViewById(R.id.auth);

        readdata = findViewById(R.id.read);
        upload = findViewById(R.id.up);
        readdata2 = findViewById(R.id.read2);
        editText = findViewById(R.id.text);
        textView = findViewById(R.id.data);
        // Write a message to the database

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("Student Data");


        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Authentication.class);
                startActivity(intent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Upload.class);
                startActivity(intent);
            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    number = childDataSnapshot.getKey();
                //    list.add(number);
                    size = list.size();


                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

            }
        });




        setdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getdata ;
               getdata = editText.getText().toString();
           /// myRef.setValue(" "+getdata);
                myRef.child(""+size).child("Name").setValue(" name "+getdata);
                myRef.child(""+size).child("Age").setValue("age "+getdata);
                myRef.child(""+size).child("gender").setValue("male "+getdata);
                Toast.makeText(MainActivity.this, " The data is set ", Toast.LENGTH_SHORT).show();

            }
        });


// Read from the database
        readdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getRef().getKey();
                     //
                        list.add(value);
                      //  Toast.makeText(MainActivity.this, ""+list, Toast.LENGTH_SHORT).show();
                        textView.setText(""+list);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                        Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        readdata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, read.class);

                startActivity(intent);

            }
        });









    }



}