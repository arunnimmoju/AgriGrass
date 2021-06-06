package com.example.agrigrass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmDetails extends AppCompatActivity {
    TextView ename;
    TextView eEmail;
    TextView eAdd;
    TextView ePin;
    TextView eContact;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);

        ename = findViewById(R.id.c_Name);
        eEmail = findViewById(R.id.c_Email);
        eAdd = findViewById(R.id.c_Add);
        ePin = findViewById(R.id.c_Pin);
        eContact = findViewById(R.id.c_Phone);
btnConfirm = findViewById(R.id.btncnfm);

        Intent intent = getIntent();
        String cName = intent.getStringExtra("custName");
        String cmail = intent.getStringExtra("custEmail");
        String eadd = intent.getStringExtra("custAddress");
        String epin = intent.getStringExtra("custPin");
        String ephn = intent.getStringExtra("custPhone");

        ename.setText(cName);
        eEmail.setText(cmail);
        eAdd.setText(eadd);
        ePin.setText(epin);
        eContact.setText(ephn);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(ConfirmDetails.this,ConfirmOrder.class);
                startActivity(intent);
            }
        });

    }
}