package com.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.main.R;
import com.main.database.OrderHistoryDatabaseHelper;
import com.main.database.OrderHistoryModel;
import com.main.database.OrderListDatabaseHelper;
import com.main.database.OrderModel;

public class OrderDetails extends AppCompatActivity {
    private ImageButton creditB, productB, orderB;
    private Button cashB, loanB;
    private double amountReceived;
    private EditText cusName, totalAmt, amountReceivedtxt, changeAmt;
    public static Boolean credit;
    OrderHistoryModel orderHistoryModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_order_details);
        cusName = findViewById(R.id.customerNameTxt);
        totalAmt = findViewById(R.id.totalAmountTxt);
        amountReceivedtxt = findViewById(R.id.amountTxt);
        changeAmt = findViewById(R.id.changeTxt);

        OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(OrderDetails.this);
        double tA = orderListDatabaseHelper.getSum();
        totalAmt.setText(""+tA);

        amountReceivedtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    amountReceived = Double.parseDouble(amountReceivedtxt.getText().toString());
                    if (amountReceivedtxt.getText().toString().trim().equals("")) {
                        throw new Exception();
                    }
                    else {
                        Double change = amountReceived - tA;
                        if(change<=0){
                            changeAmt.setText(""+0);
                        }
                        else {
                            changeAmt.setText("" + change);
                        }
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Amount Received is cleared", Toast.LENGTH_SHORT).show();
                    changeAmt.setText(""+0);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        cashB = findViewById(R.id.cashButton);
        cashB.setOnClickListener(new View.OnClickListener() {
            double change, totalAmount, receivedAmt;
            @Override
            public void onClick(View v) {
                try {
                    change = Double.parseDouble(changeAmt.getText().toString());
                    totalAmount = Double.parseDouble(totalAmt.getText().toString());

                    if(amountReceivedtxt.getText().toString().equals("")){
                        receivedAmt =0;
                    }
                    else {
                        receivedAmt = Double.parseDouble(amountReceivedtxt.getText().toString());
                    }


                    if(cusName.getText().toString().trim().equals("")){
                        throw new Exception();
                    }
                    else {
                        if (receivedAmt <= 0) {
                            orderHistoryModel = new OrderHistoryModel(-1, cusName.getText().toString(), totalAmount, true, totalAmount);
                            Toast.makeText(getApplicationContext(), Double.valueOf(totalAmt.getText().toString()) + " has been added to " + cusName.getText().toString() + "'s debt", Toast.LENGTH_SHORT).show();
                            credit = true;
                        }

                        else if (receivedAmt > 0 && receivedAmt < totalAmount) {
                            double creditAmount = totalAmount - receivedAmt;
                            orderHistoryModel = new OrderHistoryModel(-1, cusName.getText().toString(), totalAmount, true, creditAmount);
                            Toast.makeText(getApplicationContext(), creditAmount + " has been added to " + cusName.getText().toString() + "'s debt", Toast.LENGTH_SHORT).show();
                            credit = true;
                        }

                        else if (receivedAmt >= totalAmount) {
                            orderHistoryModel = new OrderHistoryModel(-1, cusName.getText().toString(), totalAmount, false,0);
                            Toast.makeText(getApplicationContext(), "Added to total sales", Toast.LENGTH_SHORT).show();
                            credit = false;
                        }
                        OrderHistoryDatabaseHelper orderHistoryDatabaseHelper = new OrderHistoryDatabaseHelper(OrderDetails.this);
                        Boolean success = orderHistoryDatabaseHelper.addOrder(orderHistoryModel);
                        if (success == true) {
                            openSummary();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Cannot pass an empty field in payments", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        loanB = findViewById(R.id.loanButton);
        loanB.setOnClickListener(new View.OnClickListener() {
            double checkAmount;
            @Override
            public void onClick(View v) {
                try {
                    amountReceivedtxt.setText(""+0);
                    orderHistoryModel = new OrderHistoryModel(-1, cusName.getText().toString(), Double.valueOf(totalAmt.getText().toString()), true, Double.valueOf(totalAmt.getText().toString()));
                    Toast.makeText(getApplicationContext(), Double.valueOf(totalAmt.getText().toString())+" has been added to "+cusName.getText().toString()+"'s debt", Toast.LENGTH_SHORT).show();
                    OrderHistoryDatabaseHelper orderHistoryDatabaseHelper = new OrderHistoryDatabaseHelper(OrderDetails.this);
                    Boolean success = orderHistoryDatabaseHelper.addOrder(orderHistoryModel);
                    if (success==true){
                        credit=true;
                        openSummary();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Recheck the forms", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

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

    public void openProducts(){
        Intent intent = new Intent(this,Products.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openSummary(){
        Intent intent = new Intent(this,OrderSummary.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}