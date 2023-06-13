package com.ethiopia.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class read extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef,myRef2;
    ArrayList list,list1,list2;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);

        recyclerView =findViewById(R.id.list);
        list=new ArrayList<>();
        list1=new ArrayList<>();
        list2=new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
     //   RecyclerView.LayoutManager LinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Student Data");
        myRef2 = database.getReference("message");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String lis = childDataSnapshot.getKey();
                    String card1 = dataSnapshot.child(lis).child("Name").getValue(String.class);
                    String card2 = dataSnapshot.child(lis).child("gender").getValue(String.class);
                    String card3 = dataSnapshot.child(lis).child("Age").getValue(String.class);
                    list.add(card1);
                    list1.add(card2);
                    list2.add(card3);
                    recyclerView.setAdapter(new MSAdapter(list,list1,list2));
                    //     Toast.makeText(MainActivity.this, ""+list.get(1), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(read.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(read.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);



                        myRef.child(position+"").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(read.this, "onSuccess ", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(read.this, " addOnFailureListener ", Toast.LENGTH_SHORT).show();
                            }
                        });




                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




    }
}