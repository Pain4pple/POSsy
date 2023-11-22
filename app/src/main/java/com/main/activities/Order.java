package com.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.main.R;
import com.main.adapters.RecycleViewAdapter;
import com.main.database.OrderListDatabaseHelper;
import com.main.database.OrderModel;

import java.util.List;

public class Order extends AppCompatActivity {
    public static double TotalValue;
    public static List<OrderModel> currentCart;
    private ImageButton creditB, productB, orderB;
    private Button chargeButton;
    private TextView label;
    private String name, image;
    private int qty;
    private double value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_order);
        OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(this);
        currentCart = orderListDatabaseHelper.getOrderList();

        RecyclerView recyclerView = findViewById(R.id.recyclerPos);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this,currentCart);
        recycleViewAdapter.setAdapter(recycleViewAdapter);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chargeButton = findViewById(R.id.chargeButton);

        chargeButton.setText("CHARGE");
        chargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalValue = value;
                openOrderDetails();
            }
        });

        //bottom buttons

        creditB = (ImageButton) findViewById(R.id.creditButton);
        creditB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCredits();
            }
        });

        productB = (ImageButton) findViewById(R.id.productButton);
        productB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openProducts();
            }
        });

        orderB = (ImageButton) findViewById(R.id.basketButton);
        orderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You are already in Order List",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openCredits(){
        Intent intent = new Intent(this,CreditList.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openOrderDetails(){
        Intent intent = new Intent(this,OrderDetails.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openProducts(){
        Intent intent = new Intent(this,Products.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}