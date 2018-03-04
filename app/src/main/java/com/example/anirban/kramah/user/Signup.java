package com.example.anirban.kramah.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.example.anirban.kramah.email.SendMail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class Signup extends AppCompatActivity {
    private EditText usrName;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText pass;
    private Button singup;
    private int flag=-1;
    int forwork = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        usrName=(EditText)findViewById(R.id.usrname);
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
        pass=(EditText) findViewById(R.id.password);
        singup=(Button) findViewById(R.id.Signup1);

        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference loginRef=root.child("Login");

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=usrName.getText().toString();
//
//                root.child("Login").orderByKey().addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            forwork =0;
//                            if(ds.getKey().toString().equals(username)){
//                                flag=1;
//                                Toast.makeText(getApplicationContext(),username+" already exist",Toast.LENGTH_SHORT).show();
//                            }
//                            if(flag!=1) {
//                                flag=0;
//                            }
//
//                        }
//                        if(flag!=-1 && flag!=1 || forwork==-1){
//                            Bundle bundle = new Bundle();
//                            bundle.putString("UserName", usrName.getText().toString());
//                            bundle.putString("Name", name.getText().toString());
//                            bundle.putString("Phone", phone.getText().toString());
//                            bundle.putString("Password", pass.getText().toString());
//                            bundle.putString("Email", email.getText().toString());
//                            Intent intent = new Intent(Signup.this, GridViewImageDisplay.class);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            root.removeEventListener(this);
//                        }
//                        else{
//                            flag=-1;
//                            forwork = -1;
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
                root.child("Login").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            forwork =0;
                            if(ds.getKey().toString().equals(username)){
                                flag=1;
                                Toast.makeText(getApplicationContext(),username+" already exist",Toast.LENGTH_SHORT).show();
                            }
                            if(flag!=1) {
                                flag=0;
                            }

                        }
                        if(flag!=-1 && flag!=1 || forwork==-1){
                            Bundle bundle = new Bundle();
                            bundle.putString("UserName", usrName.getText().toString());
                            bundle.putString("Name", name.getText().toString());
                            bundle.putString("Phone", phone.getText().toString());
                            bundle.putString("Password", pass.getText().toString());
                            bundle.putString("Email", email.getText().toString());
                            Intent intent = new Intent(Signup.this, GridViewImageDisplay.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            root.removeEventListener(this);
                        }
                        else{
                            flag=-1;
                            forwork = -1;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        /*singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> loginUpdates = new HashMap<>();
                loginUpdates.put("Password", pass.getText().toString());
                loginUpdates.put("Name", name.getText().toString());
                loginUpdates.put("Email", email.getText().toString());
                loginRef.child(phone.getText().toString()).updateChildren(loginUpdates);

                Toast.makeText(Signup.this,"Successfully Signup",Toast.LENGTH_LONG).show();

                send_email_notification();
                Intent intent=new Intent(Signup.this,MainActivity.class);
                startActivity(intent);


            }

            private void send_email_notification() {
                final String usr_name=name.getText().toString();
                final String input=email.getText().toString();

                String msg="Hello ".concat(usr_name).concat("\nWelcome to Kramah.");
                SendMail sm = new SendMail(Signup.this, input.toString(), "[Kramah] Welcome to Kramah", msg);
                sm.execute();
            }
        });*/

    }
}
