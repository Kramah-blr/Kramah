package com.example.anirban.kramah;

import android.content.Intent;
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
import java.util.Random;

public class Forgot_password extends AppCompatActivity {

    private EditText phn,email;
    private Button otpbtn;
    int randomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        phn=(EditText) findViewById(R.id.phnno);
        email=(EditText)findViewById(R.id.email);
        otpbtn=(Button)findViewById(R.id.otp);

        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference otpRef=root.child("OTP");
        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int START = 1000;
                int END = 9999;
                Random random = new Random();
                showRandomInteger(START, END, random);
                send_email_notification();

                Map<String, Object> otpUpdates = new HashMap<>();
                otpUpdates.put("otp",randomNumber);
                otpRef.child(phn.getText().toString()).updateChildren(otpUpdates);

                Bundle bundle=new Bundle();

                bundle.putString("phn", phn.getText().toString());

                Intent intent = new Intent(Forgot_password.this, Forgot_pass_otp.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });
    }

    private void send_email_notification() {
        final String input=email.getText().toString();
        String msg="Hello ".concat(input).concat("\nGenerated OTP is :"+randomNumber);


        SendMail sm = new SendMail(Forgot_password.this, input.toString(), "[Kramah] OTP is Genareted", msg);
        sm.execute();
    }

    private void showRandomInteger(int start, int end, Random random) {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)end - (long)start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * random.nextDouble());
        randomNumber =  (int)(fraction + start);
        Toast.makeText(Forgot_password.this,String.valueOf(randomNumber),Toast.LENGTH_SHORT).show();

    }

}
