package com.example.anirban.kramah.groupadmin;

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

public class Gr_admin_login extends AppCompatActivity {
    private Button login;
    private EditText phn;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gr_admin_login);
        phn=(EditText)findViewById(R.id.phn);
        pass=findViewById(R.id.pass);
        login=findViewById(R.id.gr_login);


        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=phn.getText().toString();
                final String password=pass.getText().toString();

                root.child("Group_Admin_Info").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getKey().equals(username)) {
                                DatabaseReference passref = root.child("Group_Admin_Info/".concat(ds.getKey().toString()));

                                passref.child("grppass").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot passdataSnapshot) {
                                        if (passdataSnapshot.getValue().toString().equals(password)) {
                                            Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(getApplicationContext(), Group_Admin_Activity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Wrong Password,Try Again", Toast.LENGTH_LONG).show();
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
