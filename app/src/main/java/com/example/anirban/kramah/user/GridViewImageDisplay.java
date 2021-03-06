package com.example.anirban.kramah.user;

import android.app.ProgressDialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.example.anirban.kramah.email.SendMail;
import com.example.anirban.kramah.superAdmin.Group_Form;
import com.example.anirban.kramah.superAdmin.ImageUploadInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewImageDisplay extends AppCompatActivity {

    // Creating DatabaseReference.
    DatabaseReference databaseReference;
    GridView gridimage;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    public static List<ImageUploadInfo> list = new ArrayList<ImageUploadInfo>();
    private TextView textview;
    private GridView.LayoutParams gl;
    //private ImageButton time_filter;
    Bundle b;
    String userName,name,phone,pass,email,grp_name;
    public int timeflag=0;
    public ImageUploadInfo imageUploadInfo;
    ArrayAdapter<String> timeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_image_display);
//        timeSelector();
        b = getIntent().getExtras();
        userName = b.getString("UserName");
        name=b.getString("Name");
        phone = b.getString("Phone");
        pass = b.getString("Password");
        email = b.getString("Email");
        imageUploadInfo=new ImageUploadInfo();

        gridimage = findViewById(R.id.gridimage);


        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference loginRef = root.child("Login");
        final DatabaseReference groupRef = root.child("Group");
        grid();

        gridimage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String grp_name=list.get(i).getGroupName().toString();
                Bundle bundle = new Bundle();
                bundle.putString("GroupName", grp_name);
                bundle.putString("UserName", userName);
                bundle.putString("Phone",phone);
                bundle.putString("Name", name);
                bundle.putString("Password",pass);
                bundle.putString("Email", email);

//                Toast.makeText(getApplicationContext(),grp_name+"\n"+userName+"\n"+phone+"\n"+"\n"+name+"\n"+email+"\n"+userName,Toast.LENGTH_SHORT).show();
//
//                Map<String, Object> loginUpdates = new HashMap<>();
//                loginUpdates.put("Phone",phone);
//                loginUpdates.put("Password", pass);
//                loginUpdates.put("Name", name);
//                loginUpdates.put("Email", email);
//                loginUpdates.put("Group", grp_name);
//                //loginRef.child(userName).updateChildren(loginUpdates);
//                Map<String, Object> groupUpdates = new HashMap<>();
//                groupUpdates.put("Name", name);
//                groupUpdates.put("Email", email);
                //groupRef.child(grp_name+"/"+timeAdapter.getItem(timeflag)+"/"+userName).updateChildren(groupUpdates);

                //Toast.makeText(GridViewImageDisplay.this, "Successfully Signup", Toast.LENGTH_LONG).show();

                //send_email_notification();
                Intent intent = new Intent(getApplicationContext(), Sub_Group.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    private void grid() {
        // Setting RecyclerView size true.
//        gridimage.setHasFixedSize(true);
//
//        // Setting RecyclerView layout as LinearLayout.
//        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayImagesActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(GridViewImageDisplay.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Images From Firebase.");

        // Showing progress dialog.
        progressDialog.show();

        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference(Group_Form.Database_Path);

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);
                    //Toast.makeText(DisplayImagesActivity.this,,Toast.LENGTH_SHORT).show();
                    list.add(imageUploadInfo);

                }
                gridimage.setAdapter(new ImageAdapter(GridViewImageDisplay.this,list));

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

        });

    }

    private void send_email_notification() {
        //final String usr_name;
        final String input = email;

        String msg = "Hello ".concat(userName).concat("\nWelcome to Kramah. You are selected ").concat(grp_name).concat(" Group");
        SendMail sm = new SendMail(GridViewImageDisplay.this, input.toString(), "[Kramah] Welcome to Kramah", msg);
        sm.execute();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        list.clear();
    }
}


