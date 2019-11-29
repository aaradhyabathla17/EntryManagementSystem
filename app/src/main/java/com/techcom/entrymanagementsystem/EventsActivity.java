package com.techcom.entrymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
   RecyclerView recyclerView;
    VisitorAdapter adapter;
     List<Visitor> list;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        referenceViews();
    }

    private void referenceViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new VisitorAdapter(EventsActivity.this, list);
        recyclerView.setAdapter(adapter);
        db = FirebaseDatabase.getInstance().getReference("Visitor");
        db.addListenerForSingleValueEvent(valueEventListener);
//
//        //2. SELECT * FROM Artists WHERE id = "-LAJ7xKNj4UdBjaYr8Ju"
//        Query query = FirebaseDatabase.getInstance().getReference("Visitors").child("DlH5f3jVpUegW4Y9Am5KQ2jgIfu1")
//                .orderByChild("email");
//
//        //3. SELECT * FROM Artists WHERE country = "India"
//        Query query3 = FirebaseDatabase.getInstance().getReference("Visitors")
//                .orderByChild("country")
//                .equalTo("India");
//
//        //4. SELECT * FROM Artists LIMIT 2
//        Query query4 = FirebaseDatabase.getInstance().getReference("Visitors").limitToFirst(2);
//
//
//        //5. SELECT * FROM Artists WHERE age < 30
//        Query query5 = FirebaseDatabase.getInstance().getReference("Visitors")
//                .orderByChild("age")
//                .endAt(29);
//
//
//        //6. SELECT * FROM Artists WHERE name = "A%"
//        Query query6 = FirebaseDatabase.getInstance().getReference("Visitors")
//                .orderByChild("name")
//                .startAt("A")
//                .endAt("A\uf8ff");

        ;
        /*
         * You just need to attach the value event listener to read the values
         * for example
         * query6.addListenerForSingleValueEvent(valueEventListener)
         * */
    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
           // list.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Visitor visitor = snapshot.getValue(Visitor.class);
                    list.add(visitor);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    }

