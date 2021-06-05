package com.example.agrigrass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class updateProfileActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputEditText uName;
    AutoCompleteTextView uGender;
    TextInputEditText uOccupation;
    TextInputEditText uNumber;
    Button btnUpdateDetails;

    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    String userId;

    ImageView updateProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        uName = findViewById(R.id.updateName);
        uOccupation = findViewById(R.id.updateOccupation);
        uNumber = findViewById(R.id.updatePhone);
        btnUpdateDetails = findViewById(R.id.updateDetails);
        uGender = findViewById(R.id.updateGender);


        firebaseAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] gender = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, gender);
        uGender.setAdapter(adapter);



        btnUpdateDetails.setOnClickListener(v -> {
            String fName = uName.getText().toString();

            String fOccupation = uOccupation.getText().toString();
            String fNumber = uNumber.getText().toString();
            String fGender = uGender.getText().toString();

            if(TextUtils.isEmpty(fName)){
                uName.setError("User Name is Required");
                return;
            }
            if(TextUtils.isEmpty(fGender)){
                uGender.setError("Gender is Required");
                return;
            }

            if(TextUtils.isEmpty(fNumber)){
                uNumber.setError("Number is Required");
                return;
            }
            if(TextUtils.isEmpty(fOccupation)){
                uOccupation.setError("Occupation is Required");
                return;
            }

            userId = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("User").document(userId);

            Map<String,Object> user= new HashMap<>();
            user.put("Name",fName);
            user.put("Gender",fGender);
            user.put("Contact",fNumber);
            user.put("Occupation",fOccupation);

            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "onSuccess: User Data is stored Successfully "+ userId );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: "+ e.toString());
                }
            });
            startActivity(new Intent(getApplicationContext(),Profile.class));
        });


        updateProfileImage = findViewById(R.id.updateImage);

        updateProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery;
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult(openGallery,1000);
            }
        });

        StorageReference profileRef = storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(updateProfileImage);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                updateProfileImage.setImageURI(imageUri);
                uploadImageToDatabase(imageUri);

            }
        }
    }

    private void uploadImageToDatabase(Uri imageUri) {
        //uploading image to firebase;
        StorageReference fileRef = storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(updateProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(updateProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(updateProfileActivity.this,Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}