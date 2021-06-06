package com.example.agrigrass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class OrderDetails extends AppCompatActivity {
    private ImageView imageView;

    TextInputEditText uName;
    TextInputEditText uAddress;
    TextInputEditText uPincode;
    TextInputEditText uPhone;
    TextInputEditText uEmail;
    Button adToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        uName = findViewById(R.id.custName);
        uAddress = findViewById(R.id.custAddress);
        uPincode = findViewById(R.id.custPin);
        uPhone = findViewById(R.id.custPhone);
       adToCart= findViewById(R.id.add_to_cart);
       uEmail = findViewById(R.id.custEmail);

        adToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = uName.getText().toString();
                String fAddress = uAddress.getText().toString();
                String fNumber = uPhone.getText().toString();
                String fPin = uPincode.getText().toString();
                String fEmail =uEmail.getText().toString();

                if(TextUtils.isEmpty(fName)){
                    uName.setError("User Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(fEmail)){
                    uEmail.setError("Quantity is Required");
                    return;
                }
                if(TextUtils.isEmpty(fAddress)){
                    uAddress.setError("Address is Required");
                    return;
                }

                if(TextUtils.isEmpty(fPin)){
                    uPincode.setError("Pincode is Required");
                    return;
                } if(TextUtils.isEmpty(fNumber)){
                    uPhone.setError("Number is Required");
                    return;
                }

                Intent intent= new Intent(OrderDetails.this,ConfirmDetails.class);
                intent.putExtra("custName",fName);
                intent.putExtra("custEmail",fEmail);
                intent.putExtra("custPhone",fNumber);
                intent.putExtra("custAddress",fAddress);
                intent.putExtra("custPin",fPin);
                startActivity(intent);
            }
        });

    }
}