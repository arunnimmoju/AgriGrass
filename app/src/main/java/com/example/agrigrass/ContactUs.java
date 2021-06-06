package com.example.agrigrass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    Spinner queryType;
    EditText queryInfo;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        queryType = findViewById(R.id.querySpinner);
        queryInfo = findViewById(R.id.editTextTextMultiLine);
        btnSubmit = findViewById(R.id.query_btn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qType = queryType.toString();
                String qInfo = queryInfo.getText().toString();

                if(TextUtils.isEmpty(qType)){
                    Toast.makeText(ContactUs.this, "Select Query Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(qInfo)){
                    queryInfo.setError("Quantity is Required");
                    return;
                }
                Intent intent= new Intent(ContactUs.this,Home.class);
                Toast.makeText(ContactUs.this, "Query Submmited", Toast.LENGTH_SHORT).show();
                Toast.makeText(ContactUs.this, "Our Team will reach u soon", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });


    }
}