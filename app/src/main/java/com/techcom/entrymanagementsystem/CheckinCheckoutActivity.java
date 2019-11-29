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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CheckinCheckoutActivity extends AppCompatActivity {

    Button checkIn;
    Button checkOut;
    ExpandableLinearLayout layout;
    Button Go;
    private static final int SMS_REQUEST = 1008;
    EditText email;
    DatabaseReference db;
    String visitorName, VisitorEmail, VisitorPhone, VisitorCheckOutTime, VisitorcheckInTime;
    String HostName, HostEmail, HostPhone;
    DateFormat date;
    Calendar cal;
    Date currentLocalTime;
    String localTime;
    String HostId;
    View touchInterceptor;
    String sms;
    String visitorSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_checkout);
        referenceViews();

    }
    private void sendSms()
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"+VisitorEmail));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Thanks For Your Visit");
        intent.putExtra(Intent.EXTRA_TEXT, visitorSms);
        SmsManager smsManager1=SmsManager.getDefault();
        try {
        startActivity(Intent.createChooser(intent, "Send mail..."));
            smsManager1.sendTextMessage(VisitorPhone, null, visitorSms, null, null);
            finish();
        Log.i("Finished sending email...", "");
    } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(CheckinCheckoutActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
    }
       // SmsManager smsManager1=SmsManager.getDefault();


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
        checkIn = (Button) findViewById(R.id.btn_Entry_As_visitor);
        checkOut = (Button) findViewById(R.id.btn_visitor_exit);
        Go = (Button) findViewById(R.id.btn_go);
        email = (EditText) findViewById(R.id.et_leaving_visitor_email);
        db = FirebaseDatabase.getInstance().getReference("Visitor");
        layout = findViewById(R.id.layout_leave_Visitor);
        touchInterceptor = (View) findViewById(R.id.touch_interceptor);
        touchInterceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.collapse();
                checkOut.setVisibility(View.VISIBLE);
                checkIn.setVisibility(View.VISIBLE);
                touchInterceptor.setVisibility(View.GONE);
            }
        });
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckinCheckoutActivity.this, VisitorDetailsActivity.class);
                startActivity(intent);
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.expand();
                checkIn.setVisibility(View.GONE);
                checkOut.setVisibility(View.GONE);
                touchInterceptor.setVisibility(View.VISIBLE);
                cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:31"));
                currentLocalTime = cal.getTime();
                date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
                date.setTimeZone(TimeZone.getTimeZone("GMT+5:31"));

                localTime = date.format(currentLocalTime);


            }
        });
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                db = FirebaseDatabase.getInstance().getReference("Visitor");
                Query query = FirebaseDatabase.getInstance().getReference("Visitor").orderByChild("email").equalTo(Email);
                query.addListenerForSingleValueEvent(valueEventListener);

            }
        });
    }


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Visitor visitor = snapshot.getValue(Visitor.class);
                        HostId = visitor.getHostId();
                        visitorName = visitor.getName();
                        VisitorEmail = visitor.getEmail();
                        VisitorPhone = visitor.getPhone();
                        VisitorcheckInTime = visitor.getTime();
                        Query query1 = FirebaseDatabase.getInstance().getReference("Host").orderByChild("hostId").equalTo(HostId);
                        query1.addListenerForSingleValueEvent(valueEventListenerHost);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ValueEventListener valueEventListenerHost = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Host host = snapshot.getValue(Host.class);
                        HostName = host.getName();
                        HostPhone = host.getPhone();
                        HostEmail = host.getEmail();

                        sms = "HEY!" + HostName + "\n" + "This is to inform you that your guest has checked-out." + "\n" + "Details Of Visitor:" + "\n" + "Name: " + visitorName + "\n" + "Email: " + VisitorEmail + "\n" + "Phone: " + VisitorPhone + "\n" + "CheckIn Time: " + VisitorcheckInTime + "\n" + "CheckOut Time: " + currentLocalTime;

                        visitorSms = "HEY!" + visitorName + "\n" + "Thanks for your visit " + "\n" + "Details Of Host:" + "\n" + "Name: " + HostName + "\n" + "Email: " + HostEmail + "\n" + "Phone: " + HostPhone + "\n";
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


//
}
