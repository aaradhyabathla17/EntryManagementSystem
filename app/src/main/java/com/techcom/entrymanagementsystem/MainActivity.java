package com.techcom.entrymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btn_host;
    Button btn_visitor;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        Window window = getWindow();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        inItView();

    }
    public void inItView(){
        btn_host=(Button) findViewById(R.id.btn_host);
        btn_visitor=(Button) findViewById(R.id.btn_visitor);
        mAuth=FirebaseAuth.getInstance();
        btn_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this, LoginHostActivity.class);
                startActivity(intent);
            }
        });
        btn_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,CheckinCheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

}
