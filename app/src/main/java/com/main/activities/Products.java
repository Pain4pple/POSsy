package com.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.main.R;
import com.main.database.OrderListDatabaseHelper;
import com.main.database.ProductModel;

public class Products extends AppCompatActivity {
    private ImageButton creditB, productB, orderB;
    private ImageButton pancake,waffle,beefburger,cheeseburger,softdrinks,milkshake;
    private String name, image;
    private int qty;
    private double value;
    ProductModel productModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_products);

        pancake = (ImageButton) findViewById(R.id.trashButton);
        pancake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, "Pancake", 50, R.drawable.pancake);
                    Toast.makeText(getApplicationContext(), "Added a pancake!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "That did not work", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(Products.this);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
            }
        });

        waffle = (ImageButton) findViewById(R.id.WaffleAdd);
        waffle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, "Waffle", 65, R.drawable.waffle);
                    Toast.makeText(getApplicationContext(), "Added a waffle!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "That did not work", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(Products.this);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
            }
        });

        beefburger = (ImageButton) findViewById(R.id.BeefBurgerAdd);
        beefburger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, "Beef Burger", 145, R.drawable.bifborgir);
                    Toast.makeText(getApplicationContext(), "Added a beef burgir!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "That did not work", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(Products.this);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
            }
        });
        cheeseburger = (ImageButton) findViewById(R.id.CheeseBurgerAdd);
        cheeseburger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, "Cheese Burger", 105, R.drawable.chizborgier);
                    Toast.makeText(getApplicationContext(), "Added a cheese burgir!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "That did not work", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(Products.this);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
            }
        });
        softdrinks = (ImageButton) findViewById(R.id.SoftDrinkAdd);
        softdrinks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, "Soft Drink", 65, R.drawable.harddrinks);
                    Toast.makeText(getApplicationContext(), "Added a soft drink!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "That did not work", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(Products.this);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
            }
        });
        milkshake = (ImageButton) findViewById(R.id.MilkshakeAdd);
        milkshake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, "Milkshake", 95, R.drawable.milkshake);
                    Toast.makeText(getApplicationContext(), "Added a milkshake!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "That did not work", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(Products.this);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
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
                Toast.makeText(getApplicationContext(),"You are already in Products",Toast.LENGTH_SHORT).show();
            }
        });

        orderB = (ImageButton) findViewById(R.id.basketButton);
        orderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrder();
            }
        });
    }
    public void openCredits(){
        Intent intent = new Intent(this,CreditList.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public void openOrder(){
        Intent intent = new Intent(this,Order.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}