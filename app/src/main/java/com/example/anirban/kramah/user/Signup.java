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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class Signup extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText pass;
    private Button singup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                Bundle bundle=new Bundle();
                bundle.putString("Name", name.getText().toString());
                bundle.putString("Phone", phone.getText().toString());
                bundle.putString("Password", pass.getText().toString());
                bundle.putString("Email", email.getText().toString());
                Intent intent=new Intent(Signup.this,GridViewImageDisplay.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
