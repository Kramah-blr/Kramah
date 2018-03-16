package com.example.anirban.kramah;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anirban.kramah.groupadmin.Gr_admin_login;
import com.example.anirban.kramah.superAdmin.Admin_Login;
import com.example.anirban.kramah.user.Login;
import com.example.anirban.kramah.user.Signup;

public class First_page extends AppCompatActivity {

    private Button adminlogbtn;
    private Button signupbtn;
    private Button signinbtn;
    private Button grpAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setTitle("Kramah Software India Pvt Ltd");

        adminlogbtn = (Button) findViewById(R.id.Adminlog);
        signupbtn=(Button) findViewById(R.id.Signup);
        signinbtn=(Button) findViewById(R.id.Signin);
        grpAdmin=(Button)findViewById(R.id.gr_admin);

        adminlogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(First_page.this,Admin_Login.class);
                startActivity(intent);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(First_page.this,Signup.class);
                startActivity(intent);
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(First_page.this,Login.class);
                startActivity(intent);
            }
        });
        grpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Gr_admin_login.class);
                startActivity(intent);
            }
        });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
