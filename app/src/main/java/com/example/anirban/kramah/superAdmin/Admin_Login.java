package com.example.anirban.kramah.superAdmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Login extends AppCompatActivity {

    private EditText usrname;
    private EditText pass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        usrname=(EditText) findViewById(R.id.username);
        pass=(EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.Login);

        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=usrname.getText().toString();
                final String password=pass.getText().toString();

                root.child("AdminLog").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getKey().equals(username)) {
                                DatabaseReference passref = root.child("AdminLog/".concat(ds.getKey().toString()));

                                passref.child("Password").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot passdataSnapshot) {
                                        if (passdataSnapshot.getValue().toString().equals(password)) {
                                            Toast.makeText(Admin_Login.this, "Successfully Login", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(Admin_Login.this, AdminPage.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Admin_Login.this, "Wrong Password,Try Again", Toast.LENGTH_LONG).show();
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
