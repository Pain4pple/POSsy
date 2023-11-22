package com.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.R;
import com.main.adapters.CreditAdapter;
import com.main.adapters.RecycleViewAdapter;
import com.main.database.OrderHistoryDatabaseHelper;
import com.main.database.OrderHistoryModel;
import com.main.database.OrderListDatabaseHelper;

import java.util.List;

public class CreditList extends AppCompatActivity {
    private ImageButton creditB, productB, orderB;
    private TextView sales;
    private List<OrderHistoryModel> everySales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_credit_list);

        OrderHistoryDatabaseHelper orderListDatabaseHelper  = new OrderHistoryDatabaseHelper(this);

        everySales = orderListDatabaseHelper.getOrderList();
        RecyclerView recyclerView = findViewById(R.id.customerList);
        CreditAdapter creditAdapter = new CreditAdapter(this,everySales);
        recyclerView.setAdapter(creditAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sales =findViewById(R.id.totalSalesPOS);
        sales.setText(""+orderListDatabaseHelper.getSum());

        //bottom buttons
        creditB = (ImageButton) findViewById(R.id.creditButton);
        creditB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You are already in Credit List",Toast.LENGTH_SHORT).show();
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
                openOrder();
            }
        });
    }
    public void openProducts(){
        Intent intent = new Intent(this,Products.class);
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
