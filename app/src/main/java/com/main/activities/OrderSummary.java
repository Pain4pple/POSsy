package com.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.main.R;
import com.main.database.OrderHistoryDatabaseHelper;
import com.main.database.OrderHistoryModel;
import com.main.database.OrderListDatabaseHelper;
import com.main.database.OrderModel;

import java.util.List;

public class OrderSummary extends AppCompatActivity {
    private ImageButton creditB, productB, orderB;
    private Button newOrder;
    private TextView customerName,orderTotal,modeOfPayment, productList;
    private List<OrderHistoryModel> orderHistoryModelList;
    private List<OrderModel> orderModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_order_summary);

        OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(OrderSummary.this);
        OrderHistoryDatabaseHelper orderHistoryDatabaseHelper = new OrderHistoryDatabaseHelper(OrderSummary.this);
        orderModelList = orderListDatabaseHelper.getOrderList();
        orderHistoryModelList = orderHistoryDatabaseHelper.getOrderList();

        customerName = findViewById(R.id.customerName);
        customerName.setText(""+orderHistoryModelList.get(orderHistoryModelList.size()-1).getCusName());

        orderTotal = findViewById(R.id.orderTotal);
        orderTotal.setText(""+orderListDatabaseHelper.getSum());

        modeOfPayment = findViewById(R.id.modeOfPayment);
        if(OrderDetails.credit==true){
            modeOfPayment.setText("Added to debt");
        }
        else{
            modeOfPayment.setText("Paid with cash");
        }

        productList = findViewById(R.id.productList);
        String allProducts="";
        for(int i = 0; i<orderModelList.size();i++){
            allProducts += orderModelList.get(i).getName() +" x"+orderModelList.get(i).getQty()+"\n";
        }
        productList.setText(allProducts);

        newOrder = (Button) findViewById(R.id.createNewButton);
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(OrderSummary.this);
                orderListDatabaseHelper.clearOrderList();
                Toast.makeText(getApplicationContext(),"Making a new order",Toast.LENGTH_SHORT).show();
                openProducts();
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
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(OrderSummary.this);
                orderListDatabaseHelper.clearOrderList();
                Toast.makeText(getApplicationContext(),"Making a new order",Toast.LENGTH_SHORT).show();
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

    public void openProducts(){
        Intent intent = new Intent(this,Products.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

