package com.example.anirban.kramah.groupadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anirban.kramah.email.SendMail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.anirban.kramah.R;

public class Attendence extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView dateview;
    private Spinner spinnertimelist;
    List<String> id_list;
    List<String> name_list;
    List<String> email_list;
    private ListView list;
    ArrayAdapter<String> adapter;
    private Button submit;
    int item=0;
    String timeSelected;
    private ImageButton dateicon,sltAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        dateview=findViewById(R.id.date);
        list = (ListView) findViewById(R.id.namelist);
        submit= findViewById(R.id.submit);
        dateicon=findViewById(R.id.dateicon);
        sltAll=findViewById(R.id.sltAll);
        submit.setEnabled(false);
        final String[] id = new String[] {};
        final String[] name = new String[] {};
        final String[] email = new String[] {};
        id_list = new ArrayList<String>(Arrays.asList(name));
        name_list = new ArrayList<String>(Arrays.asList(name));
        email_list =new ArrayList<String>(Arrays.asList(email));
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, name_list);


        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        list.setAdapter(adapter);
        Date d = new Date();
        CharSequence s  = DateFormat.format("dd-MM-yyyy ", d.getTime());
        dateview.setText(s);
        dateicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datepickerdialog = DatePickerDialog.newInstance(
                        Attendence.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datepickerdialog.setThemeDark(true); //set dark them for dialog?
                datepickerdialog.vibrate(true); //vibrate on choosing date?
                datepickerdialog.dismissOnPause(true); //dismiss dialog when onPause() called?
                datepickerdialog.showYearPickerFirst(false); //choose year first?
                datepickerdialog.setAccentColor(Color.parseColor("#9C27A0")); // custom accent color
                datepickerdialog.setTitle("Please select a date"); //dialog title
                datepickerdialog.show(getFragmentManager(), "Datepickerdialog"); //show dialog
            }
        });
        spinnertimelist=findViewById(R.id.timespinner);
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertimelist.setAdapter(dataAdapter);
        dataAdapter.add("Select Batch");
        dataAdapter.notifyDataSetChanged();
        final DatabaseReference root= FirebaseDatabase.getInstance().getReference().child("Group/"+Group_Admin_Activity.gad.getName());
        final DatabaseReference attendanceroot= FirebaseDatabase.getInstance().getReference();
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    dataAdapter.add(ds.getKey());
                    dataAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinnertimelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                item=0;
                id_list.clear();
                name_list.clear();
                email_list.clear();
                adapter.notifyDataSetChanged();
                timeSelected=parent.getItemAtPosition(position).toString();

                if(parent.getItemAtPosition(position).toString().equals("Select Batch")){
                    submit.setEnabled(false);
                }
                else {
                    submit.setEnabled(true);
                    root.child(parent.getItemAtPosition(position).toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                                root.child(parent.getItemAtPosition(position).toString() + "/" + ds.getKey() + "/Name").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(final DataSnapshot nameSnapshot) {
                                        root.child(parent.getItemAtPosition(position).toString() + "/" + ds.getKey() + "/Email").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot emailSnapshot) {
//                                            name_list.add("ID : "+ds.getKey().toString()+"\nNAME : "+nameSnapshot.getValue().toString());
                                                id_list.add(ds.getKey());
                                                name_list.add(nameSnapshot.getValue().toString());
                                                email_list.add(emailSnapshot.getValue().toString());
                                                adapter.notifyDataSetChanged();
                                                Map<String, Object> statusUpdates = new HashMap<>();
                                                statusUpdates.put("Name", nameSnapshot.getValue().toString());
                                                statusUpdates.put("Status", "A");
                                                attendanceroot.child("Attendence/" + Group_Admin_Activity.gad.getName() + "/" + parent.getItemAtPosition(position).toString() + "/" + dateview.getText().toString() + "/" + ds.getKey()).updateChildren(statusUpdates);
                                                list.setItemChecked(item++, true);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = list.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(adapter.getItem(position));
                    if(checked.get(i)==false){
                     send_email_notification(name_list.get(i),email_list.get(i));
                    }if(checked.get(i)==true){
                        attendanceroot.child("Attendence/"+Group_Admin_Activity.gad.getName()+"/"+timeSelected+"/"+dateview.getText().toString()+"/"+id_list.get(i)+"/Status").setValue("P");
                    }

                }
//                String[] outputStrArr = new String[selectedItems.size()];
//                for (int i = 0; i < selectedItems.size(); i++) {
//                    outputStrArr[i] = selectedItems.get(i);
//                    attendanceroot.child("Attendence/"+Group_Admin_Activity.gad.getName()+"/"+timeSelected+"/"+dateview.getText().toString()+"/"+id_list.get(i)+"/Status").setValue("P");
//                }

            }
        });
        sltAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (item = 0; item < list.getCount(); item++) {
                    list.setItemChecked(item, false);
                    //Toast.makeText(getApplicationContext(),name_list.get(item),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void send_email_notification(String TempName,String TempEmail) {
        String msg = "Hello ".concat(TempName+",").concat("\n    Welcome to Kramah.\nYou are Absent today. ");
        SendMail sm = new SendMail(Attendence.this, TempEmail.toString(), "[Kramah] Welcome to Kramah", msg);
        sm.execute();
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String day,month,syear;
        day=String.valueOf(dayOfMonth);
        month=String.valueOf(monthOfYear+1);
        syear=String.valueOf(year);
        if(dayOfMonth<10){
            day="0"+day;
        }
        if(monthOfYear<10){
            month="0"+month;
        }
        dateview.setText(day+"-"+month+"-"+syear);
    }
}
