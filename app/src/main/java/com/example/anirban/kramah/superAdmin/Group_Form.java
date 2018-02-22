package com.example.anirban.kramah.superAdmin;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anirban.kramah.R;
import com.example.anirban.kramah.email.SendMail;
import com.example.anirban.kramah.user.GridViewImageDisplay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Group_Form extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Group_Info";
    public static final String Database_Path1="Group_Admin_Info";
    String TempGroupName,TempID,TempEmail,TempPass,TempPhone,TempName,TempRole;

    // Creating button.
    private Button ChooseButton, UploadButton;

    // Creating EditText.
    private EditText GroupName,GroupID,Email,Password,Phone,OwnerName ;
    private Spinner role;

    // Creating ImageView.
    ImageView SelectImage;

    // Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference,databaseReference1;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__form);

        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        databaseReference1=FirebaseDatabase.getInstance().getReference(Database_Path1);

        //Assign ID'S to button.
        ChooseButton = (Button)findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button)findViewById(R.id.ButtonUploadImage);

        // Assign ID's to EditText.
        GroupName = (EditText)findViewById(R.id.ImageNameEditText);
        GroupName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        GroupID=(EditText)findViewById(R.id.img_id);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        Phone =(EditText)findViewById(R.id.phone);
        role=(Spinner)findViewById(R.id.role);
        OwnerName=(EditText)findViewById(R.id.name);

        //role.setOnItemSelectedListener((Group_Form.this);
        role.setOnItemSelectedListener(Group_Form.this);
        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("User");

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        role.setAdapter(dataAdapter);

        // Assign ID'S to image view.
        SelectImage = (ImageView)findViewById(R.id.ShowImageView);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(Group_Form.this);

        // Adding click listener to Choose image button.
        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });


        // Adding click listener to Upload image button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                UploadImageFileToFirebaseStorage();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                Toast.makeText(Group_Form.this,"Image Selected",Toast.LENGTH_SHORT).show();
                //ChooseButton.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            TempGroupName = GroupName.getText().toString().trim();
                            TempID=GroupID.getText().toString().trim();
                            TempEmail=Email.getText().toString().trim();
                            TempPass=Password.getText().toString().trim();
                            TempPhone=Phone.getText().toString().trim();
                            TempName=OwnerName.getText().toString().trim();
                            TempRole=String.valueOf(role.getSelectedItem());

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Group Created Successfully ", Toast.LENGTH_LONG).show();

                            // @SuppressWarnings("VisibleForTests")
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempGroupName,TempID,TempEmail,TempPass,TempPhone,TempName,TempRole,taskSnapshot.getDownloadUrl().toString());
                            // Getting image upload ID.
                            //String ImageUploadId = databaseReference.push().getKey();
                            String ImageUploadId = databaseReference.child(TempGroupName).getKey();
                            //databaseReference.child(TempImageName+"/imageURL").setValue(taskSnapshot.getDownloadUrl().toString());

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                            // databaseReference.child(ImageUploadId).setValue(taskSnapshot.getDownloadUrl().toString());
                            String ImageUploadId1 = databaseReference1.child(TempPhone).getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference1.child(ImageUploadId1).setValue(imageUploadInfo);
                            send_email_notification();
                        }

                        private void send_email_notification() {
                            //TempGroupName,TempID,TempEmail,TempPass,TempPhone,TempName,TempRole;
                            final String usr_name=TempName;
                            final String input = TempEmail;

                            String msg = "Hello ".concat(TempName+",").concat("\n    Welcome to Kramah.\nYou are "+ TempRole + " of ").concat(TempGroupName).concat(" Group").concat("\nUser ID: "+TempPhone+"\nPassword: "+TempPass);
                            SendMail sm = new SendMail(Group_Form.this, input.toString(), "[Kramah] Welcome to Kramah", msg);
                            sm.execute();
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(Group_Form.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(Group_Form.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}