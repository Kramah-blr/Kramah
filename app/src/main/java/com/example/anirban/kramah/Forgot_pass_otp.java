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

public class Forgot_pass_otp extends AppCompatActivity {

    private EditText otp;
    private Button Submit;
    Bundle b;
    String phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_otp);

        otp=(EditText)findViewById(R.id.otp);
        Submit=(Button)findViewById(R.id.submit);
        b=getIntent().getExtras();
        phn = b.getString("phn");
        //Toast.makeText(Forgot_pass_otp.this,phn,Toast.LENGTH_SHORT).show();
        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone=phn;
                final String otpvalue=otp.getText().toString();
                root.child("OTP").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getKey().equals(phone)) {
                                DatabaseReference passref = root.child("OTP/".concat(ds.getKey().toString()));

                                passref.child("otp").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot passdataSnapshot) {
                                        if (passdataSnapshot.getValue().toString().equals(otpvalue)) {
                                            Toast.makeText(Forgot_pass_otp.this, "Correct OTP", Toast.LENGTH_LONG).show();

                                            Bundle bundle=new Bundle();
                                            bundle.putString("phn", phn);
                                            Intent intent = new Intent(Forgot_pass_otp.this, New_password.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Forgot_pass_otp.this, "Wrong OTP,Try Again", Toast.LENGTH_LONG).show();
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
