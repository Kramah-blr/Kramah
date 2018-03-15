package com.example.anirban.kramah.user;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.example.anirban.kramah.email.SendMail;
import com.example.anirban.kramah.groupadmin.Group_Admin_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ng.max.slideview.SlideView;

public class Sub_Group extends AppCompatActivity {
    Bundle b;
    String userName,name,phone,pass,email,grp_name;
    private ListView sub_group;
    ArrayAdapter<String> adapter;
    List<String> name_list;
    private SlideView slideView;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub__group);
        b = getIntent().getExtras();
        userName = b.getString("UserName");
        name=b.getString("Name");
        phone = b.getString("Phone");
        pass = b.getString("Password");
        email = b.getString("Email");
        grp_name=b.getString("GroupName");
        sub_group=findViewById(R.id.sub_group);
        slideView = (SlideView) findViewById(R.id.Reg);


        final String[] lv_name = new String[] {};
        name_list = new ArrayList<String>(Arrays.asList(lv_name));
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_single_choice, name_list);
        sub_group.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        sub_group.setAdapter(adapter);

        final DatabaseReference root= FirebaseDatabase.getInstance().getReference().child("Time/"+grp_name);
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    adapter.add(ds.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sub_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
            }
        });
        final DatabaseReference root1= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference loginRef = root1.child("Login");
        final DatabaseReference groupRef = root1.child("Group");
        slideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                // vibrate the device
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                String sub=name_list.get(pos);
                //Toast.makeText(getApplicationContext(),sub,Toast.LENGTH_SHORT).show();

                Map<String, Object> loginUpdates = new HashMap<>();
                loginUpdates.put("Phone",phone);
                loginUpdates.put("Password", pass);
                loginUpdates.put("Name", name);
                loginUpdates.put("Email", email);
                loginUpdates.put("Group", grp_name);
                loginUpdates.put("SubGroup",sub);
                loginRef.child(userName).updateChildren(loginUpdates);
                Map<String, Object> groupUpdates = new HashMap<>();
                groupUpdates.put("Name", name);
                groupUpdates.put("Email", email);
                groupRef.child(grp_name+"/"+sub+"/"+userName).updateChildren(groupUpdates);

                Toast.makeText(Sub_Group.this, "Successfully Signup", Toast.LENGTH_LONG).show();

                send_email_notification();
                Intent intent=new Intent(Sub_Group.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void send_email_notification() {
        //final String usr_name;
        final String input = email;

        String msg = "Hello ".concat(userName).concat("\nWelcome to Kramah. You are selected ").concat(grp_name).concat(" Group");
        SendMail sm = new SendMail(Sub_Group.this, input.toString(), "[Kramah] Welcome to Kramah", msg);
        sm.execute();
    }
}
