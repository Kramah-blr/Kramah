package com.example.anirban.kramah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText phone;
    private EditText pass;
    private Button login;
    private Button forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone=(EditText) findViewById(R.id.phn);
        pass=(EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.Login);
        forgot=(Button) findViewById(R.id.fgtpass);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Forgot_password.class);
                startActivity(intent);
            }
        });

        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=phone.getText().toString();
                final String password=pass.getText().toString();

                root.child("Login").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getKey().equals(username)) {
                                DatabaseReference passref = root.child("Login/".concat(ds.getKey().toString()));

                                passref.child("Password").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot passdataSnapshot) {
                                        if (passdataSnapshot.getValue().toString().equals(password)) {
                                            Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Login.this, "Wrong Password,Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }


                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}

