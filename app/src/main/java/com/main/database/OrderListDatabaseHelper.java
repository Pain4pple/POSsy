package com.main.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.main.activities.Order;

import org.w3c.dom.Attr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderListDatabaseHelper extends SQLiteOpenHelper {
    public static final String ORDERLIST_TABLE = "ORDERLIST_TABLE";
    public static final String COLUMN_ORDER_VALUE = "ORDER_VALUE";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_IMAGE = "PRODUCT_IMAG";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ORDER_QTY = "ORDER_QTY";
    public static final String COLUMN_ORDER_TOTALVALUE = "ORDER_TOTALVALUE";
    public static final String COLUMN_OVERALLVALUE = "ORDER_OVERALLVALUE";

    public OrderListDatabaseHelper(@Nullable Context context) {
        super(context, "orderPOS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ORDERLIST_TABLE  + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT_NAME + " TEXT, " + COLUMN_ORDER_QTY + " INTEGER, " + COLUMN_ORDER_VALUE + " DECIMAL(65, 2), " + COLUMN_ORDER_TOTALVALUE + " DECIMAL(65, 2), " + COLUMN_PRODUCT_IMAGE + " INTEGER, " + COLUMN_OVERALLVALUE + " DECIMAL(65, 2 ))";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Boolean addProd(ProductModel productModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        String name = productModel.getName();
        Cursor res =  db.rawQuery( "SELECT * FROM ORDERLIST_TABLE WHERE PRODUCT_NAME = ?", new String[]{name} );
        if(res.getCount()==0){
        cv.put(COLUMN_PRODUCT_NAME,productModel.getName());
        cv.put(COLUMN_ORDER_QTY,1);
        cv.put(COLUMN_ORDER_VALUE,productModel.getValue());
        cv.put(COLUMN_PRODUCT_IMAGE,productModel.getImage());
        cv.put(COLUMN_ORDER_TOTALVALUE,productModel.getValue()*1);
        long insert = db.insert(ORDERLIST_TABLE, null, cv);
        if(insert==-1) {
            return false;
        }
        else{
            return true;
        }
        }
        else{
            db.execSQL("UPDATE " + ORDERLIST_TABLE + " SET " + COLUMN_ORDER_QTY + "=" + COLUMN_ORDER_QTY + "+1" + " WHERE " + "PRODUCT_NAME=?",
                    new String[] { String.valueOf(name) } );
            db.execSQL("UPDATE " + ORDERLIST_TABLE + " SET " + COLUMN_ORDER_TOTALVALUE + "=" + COLUMN_ORDER_VALUE + "*" + COLUMN_ORDER_QTY + " WHERE " + "PRODUCT_NAME=?",
                    new String[] { String.valueOf(name) } );

                return true;
        }
        }

    public List<OrderModel> getOrderList(){
        List<OrderModel> returnList = new ArrayList<>();
        String qryString = "SELECT * FROM " + ORDERLIST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qryString,null);

        if(cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQty = cursor.getInt(2);
                double orderValue = cursor.getDouble(3);
                double orderTotal = cursor.getDouble(4);
                int productImage = cursor.getInt(5);

                OrderModel newOrder = new OrderModel(ID,productName,productQty,orderValue,orderTotal,productImage);
                returnList.add(newOrder);

            }while(cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public Boolean removeOne(ProductModel productModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        String name = productModel.getName();
        Cursor res =  db.rawQuery( "SELECT * FROM ORDERLIST_TABLE WHERE PRODUCT_NAME = ?", new String[]{name} );
        if(res.getCount()==0){
                return false;
        }
        else{
            db.execSQL("UPDATE " + ORDERLIST_TABLE + " SET " + COLUMN_ORDER_QTY + "=" + COLUMN_ORDER_QTY + "-1" + " WHERE " + "PRODUCT_NAME=?",
                    new String[] { String.valueOf(name) } );
            db.execSQL("UPDATE " + ORDERLIST_TABLE + " SET " + COLUMN_ORDER_TOTALVALUE + "=" + COLUMN_ORDER_VALUE + "*" + COLUMN_ORDER_QTY + " WHERE " + "PRODUCT_NAME=?",
                    new String[] { String.valueOf(name) } );
            db.execSQL("DELETE FROM "+ ORDERLIST_TABLE +" WHERE " + COLUMN_ORDER_QTY + "<=0");

            return true;
        }
    }
    @SuppressLint("Range")
    public double getSum() {
        SQLiteDatabase db = this.getWritableDatabase();
        Double totalOrderAmount = 0.0;

        //db.execSQL("SELECT SUM(ORDER_TOTALVALUE) AS ORDER_TOTAL FROM ORDERLIST_TABLE");
        Cursor res = db.rawQuery("SELECT SUM(" + COLUMN_ORDER_TOTALVALUE + ") as ORDER_OVERALLVALUE FROM " + ORDERLIST_TABLE, null);
        if (res.moveToFirst()) {
            totalOrderAmount = Double.valueOf(res.getInt(res.getColumnIndex(COLUMN_OVERALLVALUE)));
        }
        res.close();

        return totalOrderAmount;
    }
    public Boolean removeEntireRow(ProductModel productModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        String name = productModel.getName();
        Cursor res =  db.rawQuery( "SELECT * FROM ORDERLIST_TABLE WHERE PRODUCT_NAME = ?", new String[]{name} );
        if(res.getCount()==0){
            return false;
        }
        else{
            db.execSQL("DELETE FROM "+ ORDERLIST_TABLE +" WHERE " + COLUMN_PRODUCT_NAME + "= ?",new String[]{String.valueOf(name)});

            return true;
        }
    }

    public Boolean clearOrderList(){
        SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM "+ ORDERLIST_TABLE);
            return true;
        }
    }



