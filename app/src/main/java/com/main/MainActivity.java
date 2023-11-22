package com.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.main.activities.Products;

public class MainActivity extends AppCompatActivity {
    private ImageButton smile;

    //this loads up the splash art screen or aka the landing screen of the application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        smile = (ImageButton) findViewById(R.id.imageButton);
        smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProducts();
            }
        });

    }
    public void openProducts(){
        Intent intent = new Intent(this, Products.class);
        startActivity(intent);
    }
}