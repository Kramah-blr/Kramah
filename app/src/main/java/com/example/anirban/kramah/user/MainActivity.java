package com.example.anirban.kramah.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anirban.kramah.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onBackPressed()
    {
        //super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
