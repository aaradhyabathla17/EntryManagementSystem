package com.techcom.entrymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginHostActivity extends AppCompatActivity {
 Button btn_login;
    FirebaseFirestore db;
    EditText Name;
    EditText Id;
    EditText Phone;
    EditText Email;
    FirebaseAuth mAuth;

   String uid;
    private static int RC_SIGN_IN =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_host);
        referenceViews();

    }
    private  void referenceViews()
    {
        Name=(EditText) findViewById(R.id.et_HostName);
        Email=(EditText) findViewById(R.id.et_HostEmail);
        Phone=(EditText) findViewById(R.id.et_HostPhone);
        Id=(EditText) findViewById(R.id.et_Hostid);
        btn_login=(Button) findViewById(R.id.btn_login);
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFirebase();

                Intent intent=new Intent(LoginHostActivity.this,EventsActivity.class);
                startActivity(intent);
            }
        });

    }
    private  void  initFirebase()
    {
//
        final  Host host =new Host();
        host.setName(Name.getText().toString());
        host.setEmail(Email.getText().toString());
        host.setPhone(Phone.getText().toString());
        host.setHostId(Id.getText().toString());
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
        dbref.child("Host").push().setValue(host).addOnCompleteListener(LoginHostActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginHostActivity.this,"Successful Registration",Toast.LENGTH_LONG).show();}
                else
                {
                    Toast.makeText(LoginHostActivity.this,"Something Went Wrong.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
