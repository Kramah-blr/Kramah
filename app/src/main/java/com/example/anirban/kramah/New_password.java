package com.example.anirban.kramah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class New_password extends AppCompatActivity {

    private EditText newpass;
    private Button save;
    Bundle b;
    String phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        b=getIntent().getExtras();
        phn = b.getString("phn");
        Toast.makeText(New_password.this,phn,Toast.LENGTH_SHORT).show();
        newpass=(EditText)findViewById(R.id.newpass);
        save= (Button) findViewById(R.id.save);
        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference loginRef=root.child("Login");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> loginUpdates = new HashMap<>();
                loginUpdates.put("Password", newpass.getText().toString());
                //loginUpdates.put("Name", name.getText().toString());
                //loginUpdates.put("Email", email.getText().toString());
                loginRef.child(phn).updateChildren(loginUpdates);

                Toast.makeText(New_password.this,"Password Changedmah",Toast.LENGTH_LONG).show();

            }
        });

    }
}
