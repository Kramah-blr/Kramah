package com.example.anirban.kramah.groupadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Create_group extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Bundle b;
    private EditText sub_id;
    private Spinner time;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        b= getIntent().getExtras();
        final String phn=b.getString("id");
        sub_id=findViewById(R.id.sub);
        time=(Spinner)findViewById(R.id.time);
        save=(Button)findViewById(R.id.save);

        // Spinner click listener
        time.setOnItemSelectedListener(Create_group.this);
        // Spinner Drop down elementstime.getKey().toString()

        final List<String> categories = new ArrayList<String>();
        categories.add("Morning");
        categories.add("Noon");
        categories.add("Afternoon");
        categories.add("Evening");
        categories.add("Night");


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        time.setAdapter(dataAdapter);

        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference AdminRef=root.child("Group_Admin_Info");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub_grp_id=sub_id.getText().toString();
                String batch=String.valueOf(time.getSelectedItem());
                //Toast.makeText(getApplicationContext(),sub_grp_id+"\n"+batch+"\n"+phn,Toast.LENGTH_SHORT).show();

                Map<String, Object> grpUpdates = new HashMap<>();
                grpUpdates.put(batch, batch);
                //grpUpdates.put("ID",sub_grp_id);
                AdminRef.child(phn).child("Time").setValue(grpUpdates);
                root.child("Time/"+batch+"/"+Group_Admin_Activity.gad.getName()).setValue(phn);
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
