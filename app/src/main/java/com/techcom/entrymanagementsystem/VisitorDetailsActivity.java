package com.techcom.entrymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class VisitorDetailsActivity extends AppCompatActivity {

    EditText Name;
    EditText Email;
    EditText Phone;
    EditText HostId;
    Button checkin;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String HostName, HostEmail, HostPhone;
    String VName, VEmail, VPhone;
    DateFormat date;
    Calendar cal ;
    Date currentLocalTime ;
    String localTime;
    String sms;
    private static final int SMS_REQUEST = 1008;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_visitor_details);
        referenceViews();
    }
    private void sendSms()
    {
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setDataAndType(Uri.parse("mailto:"),"text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL,HostEmail);
//        emailIntent.putExtra(Intent.EXTRA_CC, "");
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Thanks For Your Visit");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, sms);
       Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"+HostEmail));
        intent.putExtra(Intent.EXTRA_SUBJECT, "You have a Visitor");
        intent.putExtra(Intent.EXTRA_TEXT, sms);
        SmsManager smsManager1=SmsManager.getDefault();
        smsManager1.sendTextMessage(HostPhone, null, sms, null, null);
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
            smsManager1.sendTextMessage(HostPhone, null, sms, null, null);
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(VisitorDetailsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }



    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==SMS_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_LONG).show();
                sendSms();


            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void referenceViews() {
        Name = (EditText) findViewById(R.id.et_name_of_visitor);
        Email = (EditText) findViewById(R.id.et_email_of_visitor);
        Phone = (EditText) findViewById(R.id.et_phone_of_visitor);
        HostId = (EditText) findViewById(R.id.et_HostId);
        checkin = (Button) findViewById(R.id.btn_checkIn);
        // currentTime = Calendar.getInstance().getTime();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:31"));
        currentLocalTime = cal.getTime();
        date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:31"));

        localTime = date.format(currentLocalTime);
        String uid = mAuth.getCurrentUser().getUid();
//    ) findViewById(R.id.btn_checkIn);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Visitor visitor = new Visitor();
                visitor.setName(Name.getText().toString());
                visitor.setEmail(Email.getText().toString());
                visitor.setPhone(Phone.getText().toString());
                VName=visitor.getName();
                VEmail=visitor.getEmail();
                VPhone=visitor.getPhone();
                String time = localTime;

                visitor.setTime(time);
                visitor.setHostId(HostId.getText().toString());
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                dbref.child("Visitor").push().setValue(visitor);
                Query query1 = FirebaseDatabase.getInstance().getReference("Host").orderByChild("hostId").equalTo(HostId.getText().toString());
                query1.addListenerForSingleValueEvent(valueEventListenerHost);
                Intent intent=new Intent(VisitorDetailsActivity.this,MainActivity.class);
                startActivity(intent);


    }
        });
    }

    ValueEventListener valueEventListenerHost = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Host host = snapshot.getValue(Host.class);
                    HostName = host.getName();
                    HostPhone = host.getPhone();
                    HostEmail = host.getEmail();

                    sms = "HEY! " + HostName + "\n" + "This is to inform you have a visitor." + "\n" + "Details Of Visitor:" + "\n" + "Name: " + VName + "\n" + "Email: " + VEmail + "\n" + "Phone: " + VPhone + "\n" + "CheckIn Time: " + localTime ;
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST);
                    } else {
                        sendSms();
                    }
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
