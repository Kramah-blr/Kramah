package com.example.anirban.kramah.superAdmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anirban.kramah.R;

public class AdminPage extends AppCompatActivity {
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        create=(Button)findViewById(R.id.crt_gp);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Group_Form.class);
                startActivity(intent);
            }
        });
    }
}
