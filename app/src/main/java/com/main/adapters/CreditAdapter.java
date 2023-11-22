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
import com.main.database.OrderHistoryDatabaseHelper;
import com.main.database.OrderHistoryModel;
import com.main.database.OrderListDatabaseHelper;
import com.main.database.OrderModel;
import com.main.database.ProductModel;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{
    Context context;
    List<OrderHistoryModel> everySales,updatedList;
    OrderHistoryModel orderHistoryModel;
    RecycleViewAdapter globalRecycle;
    public static double TotalAmount = 0;
    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_row, parent, false);
        return new RecycleViewAdapter.MyViewHolder(view);
    }
    public CreditAdapter(Context context, List<OrderHistoryModel> everySales) {
        this.context = context;
        this.everySales = everySales;
    }



    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cusName.setText(everySales.get(position).getCusName());
        holder.totalOrdered.setText("Total Purchase:" + everySales.get(position).getTotalOrdered());
        holder.lastDateOrdered.setText(everySales.get(position).getDate());
        holder.debt.setText("Debt: "+everySales.get(position).getTotalCredit());

            holder.debtSettler.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        orderHistoryModel = new OrderHistoryModel(-1, everySales.get(position).getCusName(), everySales.get(position).getTotalOrdered(), true, everySales.get(position).getTotalCredit());
                    } catch (Exception e) {
                        orderHistoryModel = new OrderHistoryModel(-1, "error", 0, false, 0);
                    }
                    OrderHistoryDatabaseHelper orderHistoryDatabaseHelper = new OrderHistoryDatabaseHelper(context);
                    Boolean success = orderHistoryDatabaseHelper.settleDebt(orderHistoryModel);
                    if (success == true) {
                        updatedList = orderHistoryDatabaseHelper.getOrderList();
                        globalRecycle.swapDataSet2(updatedList);
                    }
                }
            });
        }



    @Override
    public int getItemCount() {
        return everySales.size();
    }

    /*public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageButton debtSettler;
        TextView cusName,totalOrdered,lastDateOrdered,debt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cusName = itemView.findViewById(R.id.cusNameOrder);
            totalOrdered = itemView.findViewById(R.id.totalPurchased);
            lastDateOrdered = itemView.findViewById(R.id.lastdateordered);
            debt = itemView.findViewById(R.id.debt);
            debtSettler = itemView.findViewById(R.id.debtSettler);

        }
    }*/

    public void setAdapter(RecycleViewAdapter recycleViewAdapter) {
        globalRecycle = recycleViewAdapter;
    }

}
