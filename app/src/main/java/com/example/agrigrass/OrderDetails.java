package com.example.agrigrass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class OrderDetails extends AppCompatActivity {


    AutoCompleteTextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        quantity = findViewById(R.id.input_quantity);

        String[] gender = new String[]{"1", "2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, gender);
        quantity.setAdapter(adapter);
    }
}