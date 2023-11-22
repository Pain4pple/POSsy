package com.main.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderHistoryDatabaseHelper extends SQLiteOpenHelper {
    public static final String ORDERHISTORY_TABLE = "ORDERHISTORY_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ORDER_TOTAL = "ORDER_TOTAL";
    public static final String COLUMN_CUS_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_ORDER_DATE = "ORDER_DATE";
    public static final String COLUMN_CREDIT= "PAID_BY_CREDIT";
    public static final String COLUMN_CREDITTOTAL = "CREDIT_TOTAL";
    public static final String COLUMN_TOTALSALES = "TOTAL_SALES";
    public static final String COLUMN_TOTALCREDITSALES = "TOTAL_CREDITONSALES";

    public OrderHistoryDatabaseHelper(@Nullable Context context) {
        super(context, "orderHistoryPOS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ORDERHISTORY_TABLE  + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUS_NAME + " TEXT, " + COLUMN_ORDER_TOTAL + " DECIMAL(65, 2), " + COLUMN_ORDER_DATE + " TEXT, " + COLUMN_CREDIT + " BOOLEAN, " +COLUMN_CREDITTOTAL + " DECIMAL(65, 2), " + COLUMN_TOTALSALES + " DECIMAL(65, 2), " + COLUMN_TOTALCREDITSALES + " DECIMAL(65, 2))";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addOrder(OrderHistoryModel orderHistoryModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        String name = orderHistoryModel.getCusName();
        Boolean credit = orderHistoryModel.getPaidByCredit();
        Cursor res =  db.rawQuery( "SELECT * FROM ORDERHISTORY_TABLE WHERE ID = ?", new String[]{name} );

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        if(res.getCount()==0){
            cv.put(COLUMN_CUS_NAME,orderHistoryModel.getCusName());
            cv.put(COLUMN_ORDER_TOTAL,orderHistoryModel.getTotalOrdered());
            cv.put(COLUMN_ORDER_DATE,formattedDate);
            cv.put(COLUMN_CREDIT, orderHistoryModel.getPaidByCredit());
            if(credit==true) {
                cv.put(COLUMN_CREDITTOTAL, orderHistoryModel.getTotalCredit());
            }

            long insert = db.insert(ORDERHISTORY_TABLE, null, cv);
            if(insert==-1) {
                return false;
            }
            else{
                return true;
            }
        }
        else{
            db.execSQL("UPDATE " + ORDERHISTORY_TABLE + " SET " + COLUMN_CREDIT + "=" + orderHistoryModel.getPaidByCredit()  + " WHERE " + "CUSTOMER_NAME=?",
                    new String[] { String.valueOf(name) } );
            db.execSQL("UPDATE " + ORDERHISTORY_TABLE + " SET " + COLUMN_CREDITTOTAL + "=+" + orderHistoryModel.getTotalCredit() + " WHERE " + "CUSTOMER_NAME=?",
                    new String[] { String.valueOf(name) } );
            db.execSQL("UPDATE " + ORDERHISTORY_TABLE + " SET " + COLUMN_ORDER_DATE + " = DATE('now') WHERE CUSTOMER_NAME=?",
                    new String[] { String.valueOf(name) } );
            return true;
        }
    }

    public List<OrderHistoryModel> getOrderList(){
        List<OrderHistoryModel> returnList = new ArrayList<>();
        String qryString = "SELECT * FROM " + ORDERHISTORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qryString,null);

        if(cursor.moveToFirst()){
            do{
                String cusName = cursor.getString(1);
                double orderTotal = cursor.getInt(2);
                String dateOrdered = cursor.getString(3);
                double totalCredit = cursor.getDouble(5);

                OrderHistoryModel orderHistoryModel = new OrderHistoryModel(cusName,orderTotal,dateOrdered,totalCredit);
                returnList.add(orderHistoryModel);

            }while(cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }
    @SuppressLint("Range")
    public double getSum() {
        SQLiteDatabase db = this.getWritableDatabase();
        Double totalOrderAmount = 0.0;

        Cursor res = db.rawQuery("SELECT SUM(" + COLUMN_ORDER_TOTAL + ") as TOTAL_SALES FROM " + ORDERHISTORY_TABLE, null);
        if (res.moveToFirst()) {
            totalOrderAmount = Double.valueOf(res.getInt(res.getColumnIndex(COLUMN_TOTALSALES)));
        }
        res.close();
        res = db.rawQuery("SELECT SUM(" + COLUMN_CREDITTOTAL + ") as TOTAL_CREDITONSALES FROM " + ORDERHISTORY_TABLE, null);
        if (res.moveToFirst()) {
            totalOrderAmount = totalOrderAmount - Double.valueOf(res.getInt(res.getColumnIndex(COLUMN_TOTALCREDITSALES)));
        }
        return totalOrderAmount;
    }

    public Boolean settleDebt(OrderHistoryModel orderHistoryModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        String name = orderHistoryModel.getCusName();
        Boolean credit = orderHistoryModel.getPaidByCredit();
        Cursor res =  db.rawQuery( "SELECT * FROM ORDERHISTORY_TABLE WHERE ID = ?", new String[]{name} );


        if(res.getCount()==0) {
            return false;
        }
        else{
            db.execSQL("UPDATE " + ORDERHISTORY_TABLE + " SET " + COLUMN_CREDIT + "= 0 WHERE " + "CUSTOMER_NAME=?",
                    new String[] { String.valueOf(name) } );
            db.execSQL("UPDATE " + ORDERHISTORY_TABLE + " SET " + COLUMN_CREDITTOTAL + "= 0 WHERE " + "CUSTOMER_NAME=?",
                    new String[] { String.valueOf(name) } );
            return true;
        }

    }
}
