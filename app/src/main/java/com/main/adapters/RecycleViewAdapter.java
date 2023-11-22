package com.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.R;
import com.main.database.OrderHistoryModel;
import com.main.database.OrderListDatabaseHelper;
import com.main.database.OrderModel;
import com.main.database.ProductModel;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{
    Context context;
    List<OrderModel> currentCart,updatedList;
    List<OrderHistoryModel> everySales,updatedSales;
    ProductModel productModel;
    RecycleViewAdapter globalRecycle;
    public static double TotalAmount = 0;
    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new RecycleViewAdapter.MyViewHolder(view);
    }
    public RecycleViewAdapter(Context context, List<OrderModel> orderModels) {
        this.context = context;
        this.currentCart = orderModels;
    }

    public void swapDataSet(List<OrderModel> orderModels){
        this.currentCart = orderModels;
        globalRecycle.notifyDataSetChanged();
    }

    public void swapDataSet2( List<OrderHistoryModel> updatedList){
        this.everySales = updatedList;
        globalRecycle.notifyDataSetChanged();
    }

    public List<OrderModel> getUpdatedList(){
        OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(context);
        updatedList = orderListDatabaseHelper.getOrderList();
        return updatedList;
    }
    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(currentCart.get(position).getName());
        holder.productQty.setText("Qty: "+currentCart.get(position).getQty() +"\nTotal: " + currentCart.get(position).getTotalValue());
        holder.productImage.setImageResource(currentCart.get(position).getImage());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, currentCart.get(position).getName(), currentCart.get(position).getTotalValue(), currentCart.get(position).getImage());
                }
                catch(Exception e){
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(context);
                Boolean success = orderListDatabaseHelper.addProd(productModel);
                if(success==true){
                    updatedList = orderListDatabaseHelper.getOrderList();
                    globalRecycle.swapDataSet(updatedList);
                }
            }
        });
        holder.reduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, currentCart.get(position).getName(), currentCart.get(position).getTotalValue(), currentCart.get(position).getImage());
                }
                catch(Exception e){
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(context);
                Boolean success = orderListDatabaseHelper.removeOne(productModel);
                if(success==true){
                    updatedList = orderListDatabaseHelper.getOrderList();
                    globalRecycle.swapDataSet(updatedList);
                }
            }
        });

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    productModel = new ProductModel(-1, currentCart.get(position).getName(), currentCart.get(position).getTotalValue(), currentCart.get(position).getImage());
                }
                catch(Exception e){
                    productModel = new ProductModel(-1, "error", 0, 0);
                }
                OrderListDatabaseHelper orderListDatabaseHelper = new OrderListDatabaseHelper(context);
                Boolean success = orderListDatabaseHelper.removeEntireRow(productModel);
                if(success==true){
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    updatedList = orderListDatabaseHelper.getOrderList();
                    globalRecycle.swapDataSet(updatedList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentCart.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        ImageButton addButton,reduceButton,trashButton;
        TextView productName, productQty;
        ImageButton debtSettler;
        TextView cusName,totalOrdered,lastDateOrdered,debt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productLabel);
            productQty = itemView.findViewById(R.id.productQty);
            addButton = itemView.findViewById(R.id.addButton);
            reduceButton = itemView.findViewById(R.id.reduceButton);
            trashButton = itemView.findViewById(R.id.trashButton);


            cusName = itemView.findViewById(R.id.cusNameOrder);
            totalOrdered = itemView.findViewById(R.id.totalPurchased);
            lastDateOrdered = itemView.findViewById(R.id.lastdateordered);
            debt = itemView.findViewById(R.id.debt);
            debtSettler = itemView.findViewById(R.id.debtSettler);

        }
    }

    public void setAdapter(RecycleViewAdapter recycleViewAdapter) {
        globalRecycle = recycleViewAdapter;
    }

}
