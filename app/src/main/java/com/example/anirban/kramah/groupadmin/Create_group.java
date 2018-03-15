package com.example.anirban.kramah.groupadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ng.max.slideview.SlideView;

public class Create_group extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener,View.OnClickListener {
    Bundle b;
    private SlideView slideView;
    private EditText sub_id;
    //private Spinner time;
    String hourString,minuteString,time,stime,etime;
    String batch;
    private int flag = 0;

    private TextView starttimeview,endtimeview;
    String timeSelected;
    private ImageButton starttimeicon,endtimeicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        b= getIntent().getExtras();
        final String phn=b.getString("id");
        sub_id=findViewById(R.id.sub);
        //time=(Spinner)findViewById(R.id.time);


        starttimeview=findViewById(R.id.starttime);
        starttimeicon=findViewById(R.id.starttimeicon);
        endtimeview=findViewById(R.id.endtime);
        endtimeicon=findViewById(R.id.endtimeicon);

        starttimeicon.setOnClickListener(this);
        endtimeicon.setOnClickListener(this);

        slideView = (SlideView) findViewById(R.id.slideSave);
        final DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference AdminRef=root.child("Group_Admin_Info");
        slideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                // vibrate the device
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                batch=Group_Admin_Activity.gad.getName()+" "+stime+" to "+etime;
                Toast.makeText(getApplicationContext(),batch,Toast.LENGTH_SHORT).show();
                Map<String, Object> grpUpdates = new HashMap<>();
                grpUpdates.put(batch, batch);
                //grpUpdates.put("ID",sub_grp_id);
                AdminRef.child(phn).child("Time").updateChildren(grpUpdates);

                //root.child("Time/"+batch+"/"+Group_Admin_Activity.gad.getName()).setValue(phn);
                root.child("Time/"+Group_Admin_Activity.gad.getName()+"/"+batch).setValue(phn);
            }
        });

        // Spinner click listener
        //time.setOnItemSelectedListener(Create_group.this);
        // Spinner Drop down elementstime.getKey().toString()

//        final List<String> categories = new ArrayList<String>();
//        categories.add("Morning");
//        categories.add("Noon");
//        categories.add("Afternoon");
//        categories.add("Evening");
//        categories.add("Night");


        // Creating adapter for spinner
        //final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //time.setAdapter(dataAdapter);


//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String sub_grp_id=sub_id.getText().toString();
//                //String batch=String.valueOf(time.getSelectedItem());
//
//                //Toast.makeText(getApplicationContext(),sub_grp_id+"\n"+batch+"\n"+phn,Toast.LENGTH_SHORT).show();
//
//                Map<String, Object> grpUpdates = new HashMap<>();
//                //grpUpdates.put(batch, batch);
//                //grpUpdates.put("ID",sub_grp_id);
//                //AdminRef.child(phn).child("Time").updateChildren(grpUpdates);
//
//                //root.child("Time/"+batch+"/"+Group_Admin_Activity.gad.getName()).setValue(phn);
//            }
//
//        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute,int second) {

        hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        minuteString = minute < 10 ? "0" + minute : "" + minute;
        //String secondString = second < 10 ? "0" + second : "" + second;
        time =  hourString + ":" + minuteString ;
        if(flag==1){
            stime=time;
            starttimeview.setText(time);
        }
        if (flag==0){
            etime=time;
            endtimeview.setText(time);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == starttimeicon) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(Create_group.this,
                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

            timepickerdialog.setThemeDark(false); //Dark Theme?
            timepickerdialog.vibrate(true); //vibrate on choosing time?
            timepickerdialog.dismissOnPause(false); //dismiss the dialog onPause() called?
            //timepickerdialog.enableSeconds(true);//show seconds?
            timepickerdialog.setAccentColor(Color.parseColor("#9C27A0"));
            //Handling cancel event
            timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(Create_group.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
                }
            });
            flag=1;
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
        }
        if (v ==endtimeicon){
            Calendar now = Calendar.getInstance();
            TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(Create_group.this,
                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

            timepickerdialog.setThemeDark(false); //Dark Theme?
            timepickerdialog.vibrate(true); //vibrate on choosing time?
            timepickerdialog.dismissOnPause(false); //dismiss the dialog onPause() called?
            //timepickerdialog.enableSeconds(true);//show seconds?
            timepickerdialog.setAccentColor(Color.parseColor("#9C27A0"));
            //Handling cancel event
            timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(Create_group.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
                }
            });
            flag=0;
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
        }
    }
}
