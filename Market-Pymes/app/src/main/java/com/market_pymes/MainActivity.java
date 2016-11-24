package com.market_pymes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Spinner dropdown = (Spinner)findViewById(R.id.listCountries);

        String[] paisesArray = {"México","Guatemala","El Salvador","Honduras","Costa Rica","Nicaragua","Panamá","República Dominicana","Colombia","Perú","Paraguay","Uruguay","Ecuador","Bolivia","Argentina"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paisesArray);
        dropdown.setAdapter(adapter);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopService(service);
                Intent newLog = new Intent(MainActivity.this,home.class);
                startActivity(newLog);
            }
        });

    }
}
